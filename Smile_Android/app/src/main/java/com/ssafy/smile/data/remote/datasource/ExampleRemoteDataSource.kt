package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.domain.model.ExampleDomainDto
import retrofit2.Response

interface ExampleRemoteDataSource {
    suspend fun exampleFunctions1(): Response<ExampleDomainDto>
    suspend fun exampleFunctions2(exampleDomainDtoParams : ExampleDomainDto): Response<ExampleDomainDto>
}