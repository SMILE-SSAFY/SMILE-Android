package com.ssafy.core.repository;

import com.ssafy.core.entity.Places;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlacesRepository extends JpaRepository<Places, Long> {
}
