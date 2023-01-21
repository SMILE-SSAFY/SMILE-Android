package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import retrofit2.Response

interface PhotographerRemoteDataSource {
    suspend fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto) : Response<Any>
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>
    suspend fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto) : Response<PhotographerResponseDto>
    suspend fun deletePhotographerInfo() : Response<String>
}
