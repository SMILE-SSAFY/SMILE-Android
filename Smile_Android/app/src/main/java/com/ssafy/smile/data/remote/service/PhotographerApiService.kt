package com.ssafy.smile.data.remote.service

import com.ssafy.smile.common.util.Constants.BASE_URL_PHOTOGRAPHER
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface PhotographerApiService {

    @POST(BASE_URL_PHOTOGRAPHER)
    suspend fun registerPhotographerInfo(@Body photographerRequestDto: PhotographerRequestDto) : Response<Any>

    @GET(BASE_URL_PHOTOGRAPHER)
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>

    @PUT(BASE_URL_PHOTOGRAPHER)
    suspend fun modifyPhotographerInfo(@Body photographerRequestDto: PhotographerRequestDto) : Response<PhotographerResponseDto>

    @DELETE(BASE_URL_PHOTOGRAPHER)
    suspend fun deletePhotographerInfo() : Response<String>

}
