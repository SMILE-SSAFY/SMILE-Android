package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerModifyDto
import com.ssafy.smile.data.remote.model.PhotographerProfile
import okhttp3.MultipartBody
import retrofit2.Response

interface PhotographerRepository {
    suspend fun registerPhotographerInfo(photographerDto: PhotographerDto, image: MultipartBody.Part)
    suspend fun getPhotographerInfo()
    suspend fun modifyPhotographerInfo(photographerDto: PhotographerModifyDto, image: MultipartBody.Part)
    suspend fun deletePhotographerInfo()
    suspend fun getPhotographerInfoByAddress(address:String, criteria: String)
    suspend fun getPhotographerProfileImg()
}

