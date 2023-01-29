package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import retrofit2.Response
import retrofit2.http.Query

interface PhotographerRemoteDataSource {
    suspend fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto) : Response<Any>
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>
    suspend fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto) : Response<PhotographerResponseDto>
    suspend fun deletePhotographerInfo() : Response<String>
    suspend fun getPhotographerInfoByAddress(address:String): Response<ArrayList<PhotographerByAddressResponseDto>>
}
