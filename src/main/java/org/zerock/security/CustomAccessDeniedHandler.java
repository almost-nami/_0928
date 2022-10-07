package org.zerock.security;

import lombok.extern.log4j.Log4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    접근 제한이 되었을 때 쿠키나 세션에 특정한 작업을 하거나
    HttpServletResponse에 특정한 헤더 정보를 추가하려면
    AccessDeniedHandler를 직접 구현하는 편이 편리함

    AccessDeniedHandler인터페이스 handle() 메소드는
    파라미터가 HttpServletRequest, HttpServletResponse이므로
    직접 서블릿 API를 이용하는 처리가 가능
 */
@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessException) throws IOException, ServletException {
        log.error("Access Denied Handler");

        log.error("Redirect.........");

        // 접근 제한이 걸리면 리다이렉트
        response.sendRedirect("/accessError");
    }
}
