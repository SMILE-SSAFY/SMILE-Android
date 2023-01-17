package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.service.ExampleApiService
import com.ssafy.smile.domain.model.ExampleDomainDto
import retrofit2.Response

class ExampleRemoteDataSourceImpl(private val exampleApiService : ExampleApiService) : ExampleRemoteDataSource{
    override suspend fun exampleFunctions1(): Response<ExampleDomainDto> {
        return exampleApiService.exampleFunction1()
    }
    override suspend fun exampleFunctions2(exampleDomainDtoParams: ExampleDomainDto): Response<ExampleDomainDto> {
        return exampleApiService.exampleFunction2(exampleDomainDtoParams)
    }
}