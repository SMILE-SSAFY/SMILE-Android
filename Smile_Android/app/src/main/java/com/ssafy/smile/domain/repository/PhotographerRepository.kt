package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import retrofit2.Response

interface PhotographerRepository {
    suspend fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto)
    suspend fun getPhotographerInfo()
    suspend fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto)
    suspend fun deletePhotographerInfo()
}
