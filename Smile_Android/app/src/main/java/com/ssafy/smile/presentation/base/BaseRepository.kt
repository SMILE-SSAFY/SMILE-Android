package com.ssafy.smile.presentation.base


import com.ssafy.smile.common.util.NetworkUtils.NetworkResponse
import retrofit2.Response


// TODO : Retrofit + Coroutine + SafeCall 처리 필요 - 1
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result : NetworkResponse<T> = safeApiResult(call, errorMessage)
        var data: T? = null
        when (result) {
            is NetworkResponse.Loading -> {  }
            is NetworkResponse.Success -> data = result.data
            is NetworkResponse.Failure -> {  }
        }
        return data
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String): NetworkResponse<T> {
        val response = call.invoke()
        if (response.isSuccessful) return NetworkResponse.Success(response.body()!!)
        return NetworkResponse.Failure(errorMessage)
    }
}