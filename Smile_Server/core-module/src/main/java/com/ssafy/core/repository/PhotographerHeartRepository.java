package com.ssafy.core.repository;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerHeart;
import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 * 사진작가 좋아요를 위한 repo
 * @author 신민철
 */

public interface PhotographerHeartRepository extends JpaRepository<PhotographerHeart, Long> {
    Optional<PhotographerHeart> findByUserAndPhotographer(User user, Photographer photographer);

    void deleteByUserAndPhotographer(User user, Photographer photographer);

    Long countByPhotographer(Photographer photographer);
}
