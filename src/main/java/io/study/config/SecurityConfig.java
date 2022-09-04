package io.study.config;

import io.study.filter.MyFilter;
import io.study.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/*
*
* @CrossOrigin은 인증이 필요 없는는 경우에만 유효
*
* */


@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationManagerConfig authenticationConfig;

    /*
    * 세션 사용 X -> 서버가 여러개일 경우? 접속자가 많을 경우? -> 하지만 Redis가 나타난다면 어떨까
    * 폼로그인 x
    * httpBasic => HTTP header -> Authorization : Id,Pw를 담아서 전송 (암호화 X)
    * Bearer => HTTP header -> Authorization : Token (세션, 폼, httpBasic 모두 비활성화해야함)
    *
    * */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        http.csrf().disable();
        http.addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(authenticationConfig)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('MANAGER') or hasRole('ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ADMIN')")
                .anyRequest().permitAll()
                ;

        return http.build();
    }
}
