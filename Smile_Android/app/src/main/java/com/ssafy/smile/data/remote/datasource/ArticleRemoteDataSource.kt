package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ClusterDto
import retrofit2.Response

interface ArticleRemoteDataSource {
    suspend fun getArticleClusterInfo(coord1x: Double, coord1y: Double, coord2x: Double, coord2y: Double) : Response<List<ClusterDto>>
}