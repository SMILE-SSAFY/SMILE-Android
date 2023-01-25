package com.ssafy.smile.domain.model

data class DialogBody(val title:String, val content:String, val customBtn:String, val closeBtn:String){
    constructor(content: String, customBtn: String, closeBtn: String) : this("", content, customBtn, closeBtn){
    }
    constructor(content: String, customBtn: String) : this("", content, customBtn, "취소"){
    }
    constructor(content: String) : this("", content, "확인", "취소"){
    }
}