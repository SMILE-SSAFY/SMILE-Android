package com.ssafy.smile.common.util

object NetworkUtils {

    sealed class NetworkResponse<out T : Any> {
        class Loading<out T: Any>: NetworkResponse<T>()
        data class Success<out T: Any>(val data: T): NetworkResponse<T>()
        data class Failure<out T: Any>(val errorCode: Int): NetworkResponse<T>()
    }
}