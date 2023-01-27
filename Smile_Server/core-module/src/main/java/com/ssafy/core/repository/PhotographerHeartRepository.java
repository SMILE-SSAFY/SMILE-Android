package com.ssafy.core.repository;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerHeart;
import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotographerHeartRepository extends JpaRepository<PhotographerHeart, Long> {
    Optional<PhotographerHeart> findByUserAndPhotographer(User user, Photographer photographer);

    Long countByPhotographer(Photographer photographer);
}
