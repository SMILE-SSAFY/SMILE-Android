package com.ssafy.core.repository;

import com.ssafy.core.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * review repo
 * @author 신민철
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
