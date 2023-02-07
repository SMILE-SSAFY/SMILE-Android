package com.ssafy.smile.common.util

// For Constant Strings
object Constants {

//    const val BASE_URL = "http://192.168.100.75:80"      // 정은
    // const val BASE_URL = "http://192.168.100.166:80"     // 재건
    // const val BASE_URL = "http://192.168.100.164:80"     // 민철
    // const val BASE_URL = "http://192.168.200.118:80"     // 지윤
    // const val BASE_URL = "http://192.168.0.25:80"        // 지윤 집
    const val BASE_URL = "https://i8d102.p.ssafy.io" // 민하 집

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