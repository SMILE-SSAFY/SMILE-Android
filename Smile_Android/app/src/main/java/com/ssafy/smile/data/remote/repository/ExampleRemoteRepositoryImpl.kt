package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.data.remote.datasource.ExampleRemoteDataSource
import com.ssafy.smile.data.remote.model.ExampleDto
import com.ssafy.smile.domain.repository.ExampleRemoteRepository


// TODO : Retrofit + Coroutine + SafeCall 처리 필요 - 2
class ExampleRemoteRepositoryImpl(private val remoteDataSource: ExampleRemoteDataSource) :
    ExampleRemoteRepository {
    override suspend fun exampleFunction(exampleDto: ExampleDto): ExampleDto {
        val response = remoteDataSource.exampleFunctions1()
        if(response.isSuccessful){
            val result = response.body()
        }
        return response.body()?.toExampleDto()!!
    }
}