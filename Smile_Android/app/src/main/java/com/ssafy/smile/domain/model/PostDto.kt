package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.ArticlePostReq
import com.ssafy.smile.data.remote.model.Post
import java.io.File

data class PostDto(var addressDomainDto: AddressDomainDto?=null, var category:String?=null, var images : List<File>?=null){
    fun toPost() = Post(
        ArticlePostReq(category!!, addressDomainDto!!.address, addressDomainDto!!.latitude, addressDomainDto!!.longitude),
        images!!
    )
}