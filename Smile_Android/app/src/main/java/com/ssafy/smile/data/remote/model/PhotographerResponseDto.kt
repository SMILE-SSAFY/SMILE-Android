package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class PhotographerResponseDto(
    val profileImg: File,
    val photographerDto : PhotographerDto
) : Parcelable