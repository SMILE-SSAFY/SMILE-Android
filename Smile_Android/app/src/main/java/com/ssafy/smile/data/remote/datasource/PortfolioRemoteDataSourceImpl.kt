package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.model.PostListResponseDto
import com.ssafy.smile.data.remote.service.PortfolioApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PortfolioRemoteDataSourceImpl(private val portfolioApiService: PortfolioApiService): PortfolioRemoteDataSource {
    override suspend fun getPortfolio(photographerId: Long): Response<PortfolioResponseDto> {
        return portfolioApiService.getPortfolio(photographerId)
    }

    override suspend fun getPosts(photographerId: Long): Response<PostListResponseDto> {
        return portfolioApiService.getPosts(photographerId)
    }
    override suspend fun uploadPost(images: MutableMap<String, RequestBody>) : Response<Any>{
        return portfolioApiService.uploadPost(images)
    }
    override suspend fun uploadPost(latitude: Double, longitude: Double,
                                    detailAddress: String, category: String, images : List<MultipartBody.Part>): Response<Any>{
        return portfolioApiService.uploadPost(latitude, longitude, detailAddress, category, images)
    }

    override suspend fun uploadPost(latitude: Double, longitude: Double,
                                    detailAddress: String, category: String, images : HashMap<String, List<MultipartBody.Part>>): Response<Any>{
        return portfolioApiService.uploadPost(latitude, longitude, detailAddress, category, images)
    }

}