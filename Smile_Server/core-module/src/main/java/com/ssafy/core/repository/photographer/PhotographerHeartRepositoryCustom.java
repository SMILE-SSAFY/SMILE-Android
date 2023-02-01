package com.ssafy.core.repository.photographer;

import com.ssafy.core.dto.PhotographerQdslDto;
import com.ssafy.core.entity.User;

import java.util.List;

public interface PhotographerHeartRepositoryCustom {
    List<PhotographerQdslDto> findByUser(User user);
}
