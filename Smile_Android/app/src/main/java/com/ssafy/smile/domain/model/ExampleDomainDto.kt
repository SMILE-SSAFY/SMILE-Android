package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.ExampleDto


data class ExampleDomainDto (
    val name : String = ""
){
    fun toExampleDto() : ExampleDto = ExampleDto(name)
}