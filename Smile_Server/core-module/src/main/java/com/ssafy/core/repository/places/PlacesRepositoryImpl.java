package com.ssafy.core.repository.places;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.QPlaces;
import lombok.RequiredArgsConstructor;

/**
 * places querydsl 클래스
 *
 * @author 서재건
 */
@RequiredArgsConstructor
public class PlacesRepositoryImpl implements PlacesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public String findByAddress(String first, String second) {
        QPlaces places = QPlaces.places;

        // TODO : 시/도 문자열 갯수 체크 최적화
        BooleanBuilder builder = new BooleanBuilder();
        if (first.length() == 2) {
            char[] chars = first.toCharArray();
            String siDo = chars[0] + "%" + chars[1] + "%";
            builder.and(places.first.like(siDo));
        } else {
            builder.and(places.first.eq(first));
        }

        return jpaQueryFactory
                .select(places.id)
                .from(places)
                .where(builder, places.second.eq(second))
                .fetchFirst();
    }
}
