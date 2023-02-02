package com.ssafy.smile.data.remote.service

import com.ssafy.smile.common.util.Constants.BASE_URL_ARTICLE
import com.ssafy.smile.data.remote.model.ClusterDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {

    @GET("$BASE_URL_ARTICLE/list")
    suspend fun getArticleClusterInfo(@Query("coord1x") coord1x: Double, @Query("coord1y") coord1y: Double,
                                      @Query("coord2x") coord2x: Double, @Query("coord2y") coord2y: Double) : Response<List<ClusterDto>>

}
