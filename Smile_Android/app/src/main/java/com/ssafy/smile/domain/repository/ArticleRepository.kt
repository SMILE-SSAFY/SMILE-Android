package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ClusterDto
import retrofit2.Response

interface ArticleRepository {
    suspend fun getArticleClusterInfo(coord1x: Double, coord1y: Double, coord2x: Double, coord2y: Double)
}
