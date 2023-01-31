package com.ssafy.smile.domain.model

data class AddressGeoDomainDto (
    val isEnabled : Boolean = false,
    val type : Types.GeoAddress = Types.GeoAddress.NETWORK_ERROR,
    val address : String = if (type== Types.GeoAddress.NETWORK_ERROR) "네트워크 오류" else if (type== Types.GeoAddress.GPS_ERROR) "잘못된 GPS 좌표"  else if (type== Types.GeoAddress.ENCODING_ERROR) "알 수 없는 주소" else ""
){

}