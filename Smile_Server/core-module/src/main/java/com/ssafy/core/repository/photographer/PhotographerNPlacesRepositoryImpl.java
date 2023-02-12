package com.ssafy.core.repository.photographer;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.PhotographerIdQdslDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.QPhotographer;
import com.ssafy.core.entity.QPhotographerNPlaces;
import com.ssafy.core.entity.QPlaces;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 사진 작가와 활동 지역 Repository Querydsl Impl
 *
 * @author 서재건
 * @author 김정은
 * @author 이지윤
 */
@Slf4j
@RequiredArgsConstructor
public class PhotographerNPlacesRepositoryImpl implements PhotographerNPlacesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    /**
     * 활동지역에 해당하는 사진작가 Id 조회
     *
     * @param addressList                   유저 주소 위치 정보
     * @return List<PhotographerIdQdslDto>  유저 주소 위치 정보로 조회한 Photographer 정보
     */

    @Override
    public List<PhotographerIdQdslDto> findPhotographerIdByAddress(String[] addressList){
        QPhotographer photographer = QPhotographer.photographer;
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPlaces places = QPlaces.places;

        BooleanBuilder builder = new BooleanBuilder();          // 동적 쿼리 생성을 위한 빌더

        if (addressList.length!=0){                             // 주소 정보 파라미터에 따른 쿼리 생성
            if (addressList.length==2){
                String second = addressList[1];
                builder.and(places.second.eq(second));
            } else {
                String first = addressList[0];
                char[] fChar = first.toCharArray();
                BooleanExpression condition = (first.length()==2)
                        ? places.first.like(fChar[0] + "%" + fChar[1] + "%") : places.first.eq(first);
                builder.and(condition);
            }
        }

        return jpaQueryFactory.select(Projections.constructor(PhotographerIdQdslDto.class, photographer.id))
                .from(photographer)
                .join(photographerNPlaces).on(photographer.eq(photographerNPlaces.photographer))
                .join(places).on(places.eq(photographerNPlaces.places))
                .where(builder)
                .groupBy(photographer.id)
                .fetch();
    }

    /**
     * placeId에 해당하는 작가 조회
     *
     * @param placeId
     * @return List<Photographer>
     */
    @Override
    public List<Photographer> findPhotographerByPlaceId(String placeId) {
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPhotographer photographer = QPhotographer.photographer;

        return jpaQueryFactory
                .select(photographer)
                .from(photographerNPlaces)
                .where(photographerNPlaces.places.id.eq(placeId))
                .fetch();
    }

    /**
     * 사진작가 별 활동지역 조회
     *
     * @param photographerId
     * @return Places
     */
    @Override
    public List<Places> findPlacesByPhotographer(Long photographerId) {
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPlaces places = QPlaces.places;

        return jpaQueryFactory
                .selectFrom(places)
                .join(photographerNPlaces).on(photographerNPlaces.places.eq(places))
                .where(photographerNPlaces.photographer.id.eq(photographerId))
                .fetch();
    }
}
