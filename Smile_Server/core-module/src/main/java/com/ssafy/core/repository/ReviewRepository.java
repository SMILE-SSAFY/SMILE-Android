package com.ssafy.core.repository;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * review repo
 * @author 신민철
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPhotographer(Photographer photographer);
}
