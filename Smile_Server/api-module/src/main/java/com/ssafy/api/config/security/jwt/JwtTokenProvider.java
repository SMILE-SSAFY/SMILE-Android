package com.ssafy.api.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * jwt 토큰 생성 관련 함수
 *
 * author @서재건
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {


    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenValidMilisecond = 1000L * 60 * 60 * 24 * 30;

    private final UserDetailsService userDetailsService;

    /**
     * secreat 키 초기화
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 유저 인덱스와 role 기반으로 jwt토큰 생성
     *
     * @param id
     * @param role
     * @return
     */
    public String createToken(String id, String role) {
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("role", role);
        claims.put("id", id);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 사용자 정보와 권한을 담은 Authentication 반환
     *
     * @param token
     * @return authentication
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdx(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰에서 유저 idx 추출
     *
     * @param token
     * @return 유저idx
     */
    private String getUserIdx(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Request Header 에서 토큰 정보 추출
     *
     * @param request
     * @return jwt token
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("bearerToken : {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            log.info("token : {}", bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 유효값 검증
     *
     * @param jwtToken
     * @return  유효값 검증 boolean
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}