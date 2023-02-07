package com.ssafy.core.repository.photographer;

import com.ssafy.core.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사진작가 관련 Repository
 *
 * @author 김정은
 */
@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
