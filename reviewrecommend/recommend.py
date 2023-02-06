from flask import Flask, jsonify
from flask import request
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from collections import defaultdict


app = Flask(__name__)

@app.route("/recommend", methods=['GET','POST'])
def hello_world():
    params = request.get_json()
    results = []
    if params:
        documents = [[] for _ in range(len(params))]
        for i,x in enumerate(params):
            documents[i] = [x["photographerId"], x["keyword"], x["heart"]]
        
        keyword_dict = defaultdict(str)

        for id, keyword, heart in documents:
            if heart == True:
                keyword_dict[0] += f" {keyword}"
            else:
                keyword_dict[id] += f" {keyword}"    

        df_form = [[i, keyword_dict[i]] for i in keyword_dict]

        df = pd.DataFrame(df_form, columns=['photographer_id', 'keyword'])

        count_vect = CountVectorizer(min_df=0, ngram_range=(1,2))

        keyword_mat = count_vect.fit_transform(df['keyword'])

        keyword_sim = cosine_similarity(keyword_mat, keyword_mat)

        keyword_sim_sorted_ind = keyword_sim.argsort()[:,::-1]

        keyword_sim_df = pd.DataFrame(keyword_sim_sorted_ind)

        print(keyword_sim_df)

        recommend_df_idx = []
        for i in range(1,len(keyword_sim_df)):
            recommend_df_idx.append(keyword_sim_df[i][0])

        print(recommend_df_idx)

        for idx in recommend_df_idx:
            photographer = int(df.loc[idx].photographer_id)
            results.append(photographer)
            print(results)
            
    if len(results) >= 10:
        return results[:10]
    else:
        return results