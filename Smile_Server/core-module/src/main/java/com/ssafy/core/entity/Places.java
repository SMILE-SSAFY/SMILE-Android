package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 활동지역 테이블
 *
 * @author 김정은
 */
@Entity
@Table(name = "places")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Places {
    @Id
    private String id;
    private String first;
    private String second;
}