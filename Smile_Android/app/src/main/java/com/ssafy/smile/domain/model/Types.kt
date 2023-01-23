package com.ssafy.smile.domain.model

object Types {
    enum class ToastType { ERROR, SUCCESS, INFO, WARNING, BASIC, CUSTOM}
    enum class ErrorType { NETWORK, TIMEOUT, UNKNOWN }
    enum class Role { USER, PHOTOGRAPHER }
    enum class Region(private val region:String) {
        SEOUL("서울특별시"), INCHEON("인천광역시"), PUSAN("부산광역시"),
        DAEGU("대구광역시"), DAEJEON("대전광역시"), ULSAN("울산광역시"),
        GUANGJU("광주광역시"), SAEJONG("세종특별자치시"),
        GEUNGGI("경기도"), GANGWON("강원도"), GEONGSANGNAMDO("경상남도"), GEONGSANGBUKDO("경상북도"),
        JEONRANAMDO("전라남도"), JEONRABUKDO("전라북도"), CHOONCHUNGNAMDO("충청남도"), CHOONCHUNGBUKDO("충청북도"), JEJUDO("제주특별자치도");

        fun getValue() : String = region

    }
}