package io.study.config;


import io.study.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
*
* FilterChain에 있는 필터들이 모두 실행된 뒤 해당 필터가 실행된다.
* 만약 필터 체인보다 빠른 실행을 원한다면 필터체인에서 addFilterBefore 메서드 사용할 것.
*
* */

@Configuration
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean<MyFilter> filter() {
//        FilterRegistrationBean<MyFilter> filter = new FilterRegistrationBean<>(new MyFilter());
//        filter.addUrlPatterns("/*");
//        filter.setOrder(0);
//
//        return filter;
//    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2() {
        FilterRegistrationBean<MyFilter2> filter = new FilterRegistrationBean<>(new MyFilter2());
        filter.addUrlPatterns("/*");
        filter.setOrder(1);

        return filter;
    }
}
