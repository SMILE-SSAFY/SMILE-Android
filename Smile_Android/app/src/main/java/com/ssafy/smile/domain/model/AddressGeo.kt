package com.ssafy.smile.domain.model

data class AddressGeo (
    val isEnabled : Boolean = false,
    val type : GeoAddress = GeoAddress.NETWORK_ERROR,
    val address : String = if (type==GeoAddress.NETWORK_ERROR) "네트워크 오류" else if (type==GeoAddress.GPS_ERROR) "잘못된 GPS 좌표"  else if (type==GeoAddress.ENCODING_ERROR) "알 수 없는 주소" else ""
){
    enum class GeoAddress(msg : String){
        NETWORK_ERROR("네트워크 오류"), GPS_ERROR("잘못된 GPS 좌표"), ENCODING_ERROR("알 수 없는 주소"), ADDRESS("")
    }
}