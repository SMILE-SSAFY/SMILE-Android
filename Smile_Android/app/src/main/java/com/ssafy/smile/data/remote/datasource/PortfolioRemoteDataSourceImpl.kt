package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ArticlePostReq
import com.ssafy.smile.data.remote.model.Post
import com.ssafy.smile.data.remote.service.PortfolioApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class PortfolioRemoteDataSourceImpl(private val portfolioApiService: PortfolioApiService) : PortfolioRemoteDataSource {
    override suspend fun uploadPost(token: String, images: MutableMap<String, RequestBody>) : Response<Any>{
        return portfolioApiService.uploadPost(token, images)
    }
    override suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                                    detailAddress: String, category: String, images : List<MultipartBody.Part>): Response<Any>{
        return portfolioApiService.uploadPost(token, latitude, longitude, detailAddress, category, images)
    }

    override suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                           detailAddress: String, category: String, images : HashMap<String, List<MultipartBody.Part>>): Response<Any>{
        return portfolioApiService.uploadPost(token, latitude, longitude, detailAddress, category, images)
    }
}