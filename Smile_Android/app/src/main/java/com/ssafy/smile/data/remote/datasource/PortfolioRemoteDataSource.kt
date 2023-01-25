package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ArticlePostReq
import com.ssafy.smile.data.remote.model.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Part
import java.io.File

interface PortfolioRemoteDataSource {
    suspend fun uploadPost(token: String, images: MutableMap<String, RequestBody>) : Response<Any>
    suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                               detailAddress: String, category: String, images : List<MultipartBody.Part>): Response<Any>
    suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                                    detailAddress: String, category: String, images : HashMap<String, List<MultipartBody.Part>>) : Response<Any>
}