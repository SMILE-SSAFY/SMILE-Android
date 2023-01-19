package com.ssafy.smile.domain.model

object Types {
    enum class ToastType { ERROR, SUCCESS, INFO, WARNING, BASIC, CUSTOM}
    enum class ErrorType { NETWORK, TIMEOUT, UNKNOWN }
    enum class Role { USER, PHOTOGRAPHER }
}