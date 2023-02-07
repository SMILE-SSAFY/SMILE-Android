package com.ssafy.core.repository;

import com.ssafy.core.entity.Places;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 장소 관련 Repository
 *
 * @author 김정은
 */
public interface PlacesRepository extends JpaRepository<Places, Long> {
}
