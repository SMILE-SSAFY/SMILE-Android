package com.ssafy.smile.domain.model

import android.text.TextWatcher
import com.ssafy.smile.data.remote.model.CategoryDto


class CategoryDomainDto(var isEmpty:Boolean = true,
                        var name: String?=null,
                        var categoryId : Int = 0,
                        var price: Int? = null,
                        var description: String?=null) {

    var priceTextWatcher: TextWatcher?=null
    var contentTextWatcher: TextWatcher?= null

    fun toCategoryDto() = CategoryDto(categoryId, name!!, price!!, description!!)
}

