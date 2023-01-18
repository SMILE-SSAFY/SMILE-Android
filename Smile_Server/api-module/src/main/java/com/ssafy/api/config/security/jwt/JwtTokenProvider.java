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

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String index, String role) {
        Claims claims = Jwts.claims().setSubject(index);
        claims.put("role", role);
        claims.put("index", index);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdx(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰에서 유저 idx 추출
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
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("authorization");
//    }

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

//    public String getUserRole(String token) {
//        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role");
//    }
}