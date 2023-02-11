package com.ssafy.smile.data.remote.datasource


import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto

import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerModifyDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import okhttp3.MultipartBody
import retrofit2.Response


interface PhotographerRemoteDataSource {
    suspend fun registerPhotographerInfo(photographerDto: PhotographerDto,image:MultipartBody.Part) : Response<Any>
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>
    suspend fun modifyPhotographerInfo(photographerDto: PhotographerModifyDto, image:MultipartBody.Part) : Response<PhotographerResponseDto>
    suspend fun deletePhotographerInfo() : Response<String>
    suspend fun getPhotographerInfoByAddress(address:String, criteria: String): Response<PhotographerByAddressResponseDto>
}
