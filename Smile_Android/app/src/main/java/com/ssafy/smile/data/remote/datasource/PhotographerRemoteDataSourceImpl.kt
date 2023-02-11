package com.ssafy.smile.data.remote.datasource


import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerModifyDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.data.remote.service.PhotographerApiService
import okhttp3.MultipartBody
import retrofit2.Response

class PhotographerRemoteDataSourceImpl(private val photographerApiService: PhotographerApiService): PhotographerRemoteDataSource {

    override suspend fun registerPhotographerInfo(photographerDto: PhotographerDto, image: MultipartBody.Part): Response<Any> {
        return photographerApiService.registerPhotographerInfo(photographerDto, image)
    }

    override suspend fun getPhotographerInfo(): Response<PhotographerResponseDto> {
        return photographerApiService.getPhotographerInfo()
    }

    override suspend fun modifyPhotographerInfo(photographerDto: PhotographerModifyDto, image: MultipartBody.Part): Response<PhotographerResponseDto> {
        return photographerApiService.modifyPhotographerInfo(photographerDto, image)
    }

    override suspend fun deletePhotographerInfo(): Response<String> {
        return photographerApiService.deletePhotographerInfo()
    }

    override suspend fun getPhotographerInfoByAddress(address:String, criteria: String): Response<PhotographerByAddressResponseDto> {
        return photographerApiService.getPhotographerInfoByAddress(address, criteria)
    }
}