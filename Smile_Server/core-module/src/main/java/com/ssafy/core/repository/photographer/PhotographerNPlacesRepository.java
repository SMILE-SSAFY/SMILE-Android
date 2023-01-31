package com.ssafy.core.repository.photographer;

import com.ssafy.core.entity.PhotographerNPlaces;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사진 작가와 활동 지역 Repository
 *
 * author @김정은
 * author @서재건
 */
public interface PhotographerNPlacesRepository extends JpaRepository<PhotographerNPlaces, Long>, PhotographerNPlacesRepositoryCustom {
}
