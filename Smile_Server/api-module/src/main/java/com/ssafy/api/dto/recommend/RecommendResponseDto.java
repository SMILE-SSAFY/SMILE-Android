package com.ssafy.api.dto.recommend;

import com.ssafy.api.dto.Photographer.PhotographerForListDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * 추천 리스폰스 Dto
 *
 * @author 이지윤
 */
@Data
@Builder
public class RecommendResponseDto {
 public Boolean isHeartEmpty;
 public List<PhotographerForListDto> photographerInfoList;

}
