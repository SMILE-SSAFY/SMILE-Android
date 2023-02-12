package com.ssafy.smile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDomainDto (
    val isMe: Boolean = false,
    val isHeart: Boolean = false,
    val profileImg: String = "",
    val hearts: Int = 0,
    val detailAddress: String = "",
    val createdAt: String = "",
    val category: String = "",
    val photographerName: String = "",
    val photoUrl: ArrayList<String> = arrayListOf(),
):Parcelable