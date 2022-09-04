package io.study.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.study.auth.PrincipalDetails;
import io.study.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/*
*
* 기본 설정 기준 로그인 시 자동으로 해당 로직 작동
*
* */

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }



    /*
    *
    * username + password
    * authenticationManager -> principalDetailsService -> loadUserByUsername()
    * Session(PrincipalDetails) -> JWT
    *
    * */


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = readUserInfo(new ObjectMapper(), request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // PrincipalDetailService -> loadUserByUsername()
        return authenticationManager.authenticate(authenticationToken);
        // authentication -> session (login)
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
//        Hash 암호 방식
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60000 * 10))
                .withClaim("id", principal.getUserDbId())
                .withClaim("username", principal.getUsername())
                .sign(Algorithm.HMAC512("security"));

        response.setHeader("Authorization","Bearer "+token);
    }

    public User readUserInfo(ObjectMapper objectMapper, HttpServletRequest request)  {
        try {
            return objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            log.info("",e);
        }
        return null;
    }
}
