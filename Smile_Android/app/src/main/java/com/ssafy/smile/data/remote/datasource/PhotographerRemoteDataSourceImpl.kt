package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.data.remote.service.PhotographerApiService
import retrofit2.Response

class PhotographerRemoteDataSourceImpl(private val photographerApiService: PhotographerApiService): PhotographerRemoteDataSource {
    override suspend fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto): Response<Any> {
        return photographerApiService.registerPhotographerInfo(photographerRequestDto)
    }

    override suspend fun getPhotographerInfo(): Response<PhotographerResponseDto> {
        return photographerApiService.getPhotographerInfo()
    }

    override suspend fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto): Response<PhotographerResponseDto> {
        return photographerApiService.modifyPhotographerInfo(photographerRequestDto)
    }

    override suspend fun deletePhotographerInfo(): Response<String> {
        return photographerApiService.deletePhotographerInfo()
    }
}