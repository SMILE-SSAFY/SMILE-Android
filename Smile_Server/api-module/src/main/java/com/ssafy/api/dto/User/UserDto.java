package com.ssafy.api.dto.User;

import com.ssafy.core.code.Role;
import com.ssafy.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 조회하는 DTO
 *
 * @author 서재건
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private Role role;
    private String photoUrl;


    /**
     * User Entity에서 User Dto로 변경
     *
     * @param user
     * @return 변환된 DTO
     */
    public UserDto of(User user, String photoUrl) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .photoUrl(photoUrl)
                .build();
    }
}
