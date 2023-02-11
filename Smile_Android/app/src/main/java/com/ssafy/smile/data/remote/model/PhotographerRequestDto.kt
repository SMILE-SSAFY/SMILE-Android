package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.AccountDomainDto
import com.ssafy.smile.domain.model.PhotographerRequestDomainDto
import java.io.File

data class PhotographerRequestDto(
    val profileImg: File,
    val photographerDto : PhotographerDto
)

data class PhotographerModifyRequestDto(
    val profileImg: File,
    val photographerDto : PhotographerModifyDto
)

