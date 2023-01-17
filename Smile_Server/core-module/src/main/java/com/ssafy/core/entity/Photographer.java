package com.ssafy.core.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "photographer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@TypeDef(name = "json", typeClass = JsonType.class)
public class Photographer implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @Column(name = "profile_img")
    private String profileImg;

    private String introduction;

    private String account;

    private int heart;

    @Column(name = "places", columnDefinition = "longtext")
    @Type(type="json")
    private List<Places> places;

}
