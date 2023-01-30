package com.ssafy.smile.data.remote.service

import com.ssafy.smile.common.util.Constants.BASE_URL_PHOTOGRAPHER
import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import retrofit2.Response
import retrofit2.http.*

interface PhotographerApiService {

    @POST(BASE_URL_PHOTOGRAPHER)
    suspend fun registerPhotographerInfo(@Body photographerRequestDto: PhotographerRequestDto) : Response<Any>

    @GET(BASE_URL_PHOTOGRAPHER)
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>

    @PUT(BASE_URL_PHOTOGRAPHER)
    suspend fun modifyPhotographerInfo(@Body photographerRequestDto: PhotographerRequestDto) : Response<PhotographerResponseDto>

    @DELETE(BASE_URL_PHOTOGRAPHER)
    suspend fun deletePhotographerInfo() : Response<String>

    @GET("$BASE_URL_PHOTOGRAPHER/list")
    suspend fun getPhotographerInfoByAddress(@Query("address") address:String): Response<ArrayList<PhotographerByAddressResponseDto>>

}
