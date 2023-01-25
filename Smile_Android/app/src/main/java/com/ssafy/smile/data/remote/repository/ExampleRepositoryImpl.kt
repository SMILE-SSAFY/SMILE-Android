package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.data.remote.datasource.ExampleRemoteDataSource
import com.ssafy.smile.data.remote.model.ExampleDto
import com.ssafy.smile.domain.repository.ExampleRepository
import com.ssafy.smile.presentation.base.BaseRepository

class ExampleRepositoryImpl(private val remoteDataSource: ExampleRemoteDataSource) : BaseRepository(),
    ExampleRepository {
    override suspend fun exampleFunction(exampleDto: ExampleDto): ExampleDto {
        val response = remoteDataSource.exampleFunctions1()
        if(response.isSuccessful){
            val result = response.body()
        }
        return response.body()?.toExampleDto()!!
    }
}