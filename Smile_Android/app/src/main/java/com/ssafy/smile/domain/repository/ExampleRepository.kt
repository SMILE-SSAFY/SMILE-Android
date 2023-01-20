package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ExampleDto

interface ExampleRepository {
    suspend fun exampleFunction(exampleDto: ExampleDto) : ExampleDto
}