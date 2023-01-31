package com.ssafy.core.repository.photographer;

import com.ssafy.core.entity.PhotographerNCategories;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository
 * querydsl 사용하기 위한 custom 인터페이스 상속
 *
 * author @김정은
 * author @서재건
 */
public interface PhotographerNCategoriesRepository extends JpaRepository<PhotographerNCategories, Long>, PhotographerNCategoriesRepositoryCustom {
}
