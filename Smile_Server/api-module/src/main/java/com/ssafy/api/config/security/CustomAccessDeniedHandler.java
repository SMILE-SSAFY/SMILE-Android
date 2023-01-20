package com.ssafy.api.config.security;

import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Forbidden 에러 캐치
 *
 * author @서재건
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * authorization 에러 처리를 위해 exception 컨트롤러로 리다이렉트
     *
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // FIX: 500 ERROR
        log.info("[hanlde]접근이 막혔을 경우");
        response.sendRedirect("/exception/authorization");
    }
}
