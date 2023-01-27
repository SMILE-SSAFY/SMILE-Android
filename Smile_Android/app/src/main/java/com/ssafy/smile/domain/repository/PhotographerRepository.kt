package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.PhotographerRequestDto

interface PhotographerRepository {
    suspend fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto)
    suspend fun getPhotographerInfo()
    suspend fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto)
    suspend fun deletePhotographerInfo()
    suspend fun getPhotographerInfoByAddress(address:String)
}
