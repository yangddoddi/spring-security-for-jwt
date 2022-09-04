package io.study.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
*
* id, pw가 정상적으로 들어오면 토큰 만들어서 응답
* 요청 시 header Authorization에 토큰을 가져옴
* 토큰 가져왔을 시에는 토큰 검증만하면 끝(DB필요 X)
* == RSA, HS256
*
* */


@Slf4j
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        log.info("req.getMethod() ? {} ", req.getMethod());
        String headerAuth = req.getHeader("Authorization");
        log.info("headerAuth = {}", headerAuth);

        if (headerAuth.equals("security")) {
            chain.doFilter(req,res);
        } else {
            throw new ServletException("잘못된 요청");
        }
    }
}
