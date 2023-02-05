package com.ssafy.smile.domain.model

object Types {
    enum class ToastType { ERROR, SUCCESS, INFO, WARNING, BASIC, CUSTOM}
    enum class ErrorType { NETWORK, TIMEOUT, UNKNOWN }
    enum class Role(private val role: String) {
        USER("user"), PHOTOGRAPHER("photographer") ;
        fun getValue() : String = role

        companion object{
            fun getRoleType(role: String) : Role {
                return when(role){
                    USER.getValue() -> USER
                    PHOTOGRAPHER.getValue() -> PHOTOGRAPHER
                    else -> USER
                }
            }
        }
    }
    enum class PostSearchType(private val typeStr : String){
        HEART("heart"), TIME("time"), DISTANCE("distance");
        fun getValue() : String = typeStr
    }

    enum class GeoAddress(msg : String) {
        NETWORK_ERROR("네트워크 오류"), GPS_ERROR("잘못된 GPS 좌표"), ENCODING_ERROR("알 수 없는 주소"), ADDRESS("")
    }

    enum class Region(private val region:String) {
        SEOUL("서울특별시"), INCHEON("인천광역시"), PUSAN("부산광역시"),
        DAEGU("대구광역시"), DAEJEON("대전광역시"), ULSAN("울산광역시"),
        GUANGJU("광주광역시"), SAEJONG("세종특별자치시"),
        GEUNGGI("경기도"), GANGWON("강원도"), GEONGSANGNAMDO("경상남도"), GEONGSANGBUKDO("경상북도"),
        JEONRANAMDO("전라남도"), JEONRABUKDO("전라북도"), CHOONCHUNGNAMDO("충청남도"), CHOONCHUNGBUKDO("충청북도"), JEJUDO("제주특별자치도");

        fun getValue() : String = region
    }

    enum class PagingRVType(private val isData : Boolean) {
        CONTENT(true), PROGRESS(false);

        fun getValue() : Boolean = isData
    }
}