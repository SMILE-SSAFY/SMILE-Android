package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.ArticlePostReq
import com.ssafy.smile.data.remote.model.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface PortfolioApiService {
    @Multipart
    @POST("/api/article")
    suspend fun uploadPost(@Header("Authorization") token: String,
                    @PartMap images: MutableMap<String, RequestBody>): Response<Any>

    @Multipart
    @POST("/api/article")
    suspend fun uploadPost(@Header("Authorization") token: String,
                           @Part("latitude") latitude : Float,
                           @Part("longitude") longitude : Float,
                           @Part("detailAddress") detailAddress : String,
                           @Part("category") category: String,
                           @Part imageList : List<MultipartBody.Part>
    ): Response<Any>

    @Multipart
    @POST("/api/article")
    suspend fun uploadPost(@Header("Authorization") token: String,
                           @Part("latitude") latitude : Float,
                           @Part("longitude") longitude : Float,
                           @Part("detailAddress") detailAddress : String,
                           @Part("category") category: String,
                           @PartMap imageList : HashMap<String, List<MultipartBody.Part>>
    ): Response<Any>

}