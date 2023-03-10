package com.ssafy.smile.data.remote.service

import com.ssafy.smile.common.util.Constants.BASE_URL_PHOTOGRAPHER
import com.ssafy.smile.data.remote.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PhotographerApiService {

    @Multipart
    @POST(BASE_URL_PHOTOGRAPHER)
    suspend fun registerPhotographerInfo(@Part("photographer") photographerDto: PhotographerDto,
                                         @Part image : MultipartBody.Part) : Response<Any>

    @GET(BASE_URL_PHOTOGRAPHER)
    suspend fun getPhotographerInfo() : Response<PhotographerResponseDto>

    @Multipart
    @PUT(BASE_URL_PHOTOGRAPHER)
    suspend fun modifyPhotographerInfo(@Part("photographer") photographer: PhotographerModifyDto,
                                       @Part image : MultipartBody.Part) : Response<PhotographerResponseDto>

    @DELETE(BASE_URL_PHOTOGRAPHER)
    suspend fun deletePhotographerInfo() : Response<String>

    @GET("$BASE_URL_PHOTOGRAPHER/list")
    suspend fun getPhotographerInfoByAddress(@Query("address") address:String, @Query("criteria") criteria: String): Response<PhotographerByAddressResponseDto>

    @GET("/api/user/img")
    suspend fun getPhotographerProfileImg(): Response<PhotographerProfile>
}
