package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import okhttp3.MultipartBody

interface PhotographerRepository {
    suspend fun registerPhotographerInfo(photographerDto: PhotographerDto, image: MultipartBody.Part)
    suspend fun getPhotographerInfo()
    suspend fun modifyPhotographerInfo(photographerDto: PhotographerDto, image: MultipartBody.Part)
    suspend fun deletePhotographerInfo()
    suspend fun getPhotographerInfoByAddress(address:String, criteria: String)
}

