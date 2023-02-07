from flask import Flask, request
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from collections import defaultdict


app = Flask(__name__)
# POST 메소드를 통해 http://localhost:5000/recommend로 요청
@app.route("/recommend", methods=['POST'])
def hello_world():
    # json raw data를 얻어온다
    params = request.get_json()
    print(params)
    # return 할 result
    results = []
    # 내가 찜한 작가들이 있다면
    if params:
        # dict to List
        documents = [[] for _ in range(len(params))]
        for i,x in enumerate(params):
            documents[i] = [x["photographerId"], x["keyword"], x["heart"]]
        
        keyword_dict = defaultdict(str)
        # list to df 
        for id, keyword, heart in documents:
            if heart == True:
                keyword_dict[0] += f" {keyword}"
            else:
                keyword_dict[id] += f" {keyword}"    

        df_form = [[i, keyword_dict[i]] for i in keyword_dict]

        df = pd.DataFrame(df_form, columns=['photographer_id', 'keyword'])
        # df에서 vectorize 진행
        count_vect = CountVectorizer(min_df=0, ngram_range=(1,2))

        keyword_mat = count_vect.fit_transform(df['keyword'])
        # 코사인 유사도를 기반으로 keyword sim 계산
        keyword_sim = cosine_similarity(keyword_mat, keyword_mat)
        # 유사도 별로 정렬
        keyword_sim_sorted_ind = keyword_sim.argsort()[:,::-1]

        keyword_sim_df = pd.DataFrame(keyword_sim_sorted_ind)

        print(keyword_sim_df)
        # 유사도 별로 정렬 후 df의 idx를 photographerId로 바꾼 후 return
        recommend_df_idx = []
        for i in range(1,len(keyword_sim_df)):
            recommend_df_idx.append(keyword_sim_df[i][0])

        print(recommend_df_idx)

        for idx in recommend_df_idx:
            photographer = int(df.loc[idx].photographer_id)
            results.append(photographer)
            print(results)
    # 최대 10개 return
    if len(results) >= 10:
        return results[:10]
    else:
        return results