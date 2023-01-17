package com.ssafy.smile.data.remote.service

import com.ssafy.smile.domain.model.ExampleDomainDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExampleApiService {
    @GET("/api/example/example1")
    suspend fun exampleFunction1() : Response<ExampleDomainDto>

    @POST("/api/example/example2")
    suspend fun exampleFunction2(@Body exampleDomainDtoParam : ExampleDomainDto) : Response<ExampleDomainDto>

}
