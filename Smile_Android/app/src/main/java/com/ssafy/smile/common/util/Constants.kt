package com.ssafy.smile.common.util

// For Constant Strings
object Constants {

    const val BASE_URL = "https://i8d102.p.ssafy.io"

    const val BASE_URL_PHOTOGRAPHER = "/api/photographer"
    const val BASE_URL_ARTICLE = "/api/article"

    // Request values
    const val REQUEST_KEY_IMAGE_PROFILE = "profileImg"
    const val REQUEST_KEY_IMAGE_LIST = "imageList"

    // Permission
    const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
    const val PERMISSION_PICTURE = android.Manifest.permission.READ_EXTERNAL_STORAGE

    // ERROR MESSAGE - PHOTOGRAPHER
    const val ERROR_DELETE_PHOTOGRAPHER = "HAS_RESERVATION"

    // s3 image
    const val IMAGE_BASE_URL = "https://smile-bucket.s3.ap-northeast-2.amazonaws.com/"

}