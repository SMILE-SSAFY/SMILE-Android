package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.ExampleDomainDto

data class ExampleDto (
    val name : String = ""
){
    fun toExampleDomainDto() : ExampleDomainDto = ExampleDomainDto(name)
}