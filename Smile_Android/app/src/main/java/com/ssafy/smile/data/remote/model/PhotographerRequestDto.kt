package com.ssafy.smile.data.remote.model

import java.io.File

data class PhotographerRequestDto(
    val profileImg: File,
    val photographerDto : PhotographerDto
)

