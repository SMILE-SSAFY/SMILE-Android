package com.ssafy.core.repository.places;

/**
 * places querydsl 인터페이스
 *
 * @author 서재건
 */
public interface PlacesRepositoryCustom {
    /**
     * 주소에 해당하는 placeId 검색
     *
     * @param first
     * @param second
     * @return placeId
     */
    String findByAddress(String first, String second);
}
