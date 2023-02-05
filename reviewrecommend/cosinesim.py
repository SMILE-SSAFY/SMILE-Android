import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from collections import defaultdict

# 유저가 좋아요 누른 작가 리뷰들
documents = [
    [1, "The sky is red", True],
    [1, "The sun is bright", True],
    [2, "The sun in the sky is bright", False],
    [2, "We can see the shining sun, the bright sun", False],
    [1, "The sky is blue", True],
    [1, "The moon is dark", True],
    [2, "The sun in the sky is bright", False],
    [2, "We can see the shining sun, the bright sun", False],
    [3, "do it something", False],
    [4, "i can do this all day", False],
    [5, "the sun do this all day",False]
]

keyword_dict = defaultdict(str)

for id, keyword, heart in documents:
    keyword_dict[id] += f" {keyword}"    

df_form = [[i, keyword_dict[i]] for i in keyword_dict]

df = pd.DataFrame(df_form, columns=['photographer_id', 'keyword'])

print(df)

count_vect = CountVectorizer(min_df=0, ngram_range=(1,2))

keyword_mat = count_vect.fit_transform(df['keyword'])

keyword_sim = cosine_similarity(keyword_mat, keyword_mat)

print(keyword_sim)

keyword_sim_sorted_ind = keyword_sim.argsort()[:,::-1]
print(keyword_sim_sorted_ind)

keyword_sim_df = pd.DataFrame(keyword_sim_sorted_ind)
print(keyword_sim_df)
print(keyword_sim_df[1].values.tolist())
print(keyword_sim_df[2].values.tolist())

# df['keyword_sim'] = keyword_sim_df.apply(lambda x: [y for y in x], axis=1)

print(df)

photographer = df[df['photographer_id'] == 1]

photoidx = photographer.index.values

similar_idx = keyword_sim_sorted_ind[photoidx, 1:].reshape(-1)

print(df.iloc[similar_idx])