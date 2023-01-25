package com.ssafy.smile.domain.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ArticlePostReq
import com.ssafy.smile.data.remote.model.Post
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

interface PortfolioRepository {
    suspend fun uploadPost(token: String, images: MutableMap<String, RequestBody>)
    suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                           detailAddress: String, category: String, images : List<MultipartBody.Part>)
    suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                           detailAddress: String, category: String, images : HashMap<String, List<MultipartBody.Part>>)
}