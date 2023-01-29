package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part

interface PhotographerRemoteDataSource {
    suspend fun registerPhotographerInfo(photographerDto: PhotographerDto,image:MultipartBody.Part) : Response<Any>
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>
    suspend fun modifyPhotographerInfo(photographerDto: PhotographerDto,image:MultipartBody.Part) : Response<PhotographerResponseDto>
    suspend fun deletePhotographerInfo() : Response<String>
}
