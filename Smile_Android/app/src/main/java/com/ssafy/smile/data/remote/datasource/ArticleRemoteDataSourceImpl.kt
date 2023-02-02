package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ClusterDto
import com.ssafy.smile.data.remote.service.ArticleApiService
import retrofit2.Response

class ArticleRemoteDataSourceImpl(private val articleApiService: ArticleApiService): ArticleRemoteDataSource {
    override suspend fun getArticleClusterInfo(coord1x: Double, coord1y: Double, coord2x: Double, coord2y: Double): Response<List<ClusterDto>> {
        return articleApiService.getArticleClusterInfo(coord1x, coord1y, coord2x, coord2y)
    }
}