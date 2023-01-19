package com.ssafy.smile.domain.model

object Types {
    enum class ToastType { ERROR, SUCCESS, INFO, WARNING, BASIC, CUSTOM}
    enum class ErrorType { NETWORK, TIMEOUT, UNKNOWN }
    enum class GeoAddress(msg : String){
        NETWORK_ERROR("네트워크 오류"), GPS_ERROR("잘못된 GPS 좌표"), ENCODING_ERROR("알 수 없는 주소"), ADDRESS("")
    }
}