package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.data.remote.datasource.ExampleRemoteDataSource
import com.ssafy.smile.data.remote.datasource.PortfolioRemoteDataSourceImpl
import com.ssafy.smile.data.remote.model.ExampleDto
import com.ssafy.smile.domain.repository.PortfolioRepository
import com.ssafy.smile.presentation.base.BaseRepository

class PortfolioRepositoryImpl(private val portfolioRemoteDataSource: PortfolioRemoteDataSourceImpl) : BaseRepository(), PortfolioRepository {
//    override suspend fun exampleFunction(exampleDto: ExampleDto): ExampleDto {
//        val response = remoteDataSource.exampleFunctions1()
//        if(response.isSuccessful){
//            val result = response.body()
//        }
//        return response.body()?.toExampleDto()!!
//    }
}