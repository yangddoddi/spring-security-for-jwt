package io.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
*
* 기본 구성
* BASE64(Header)
* BASE64(Payload)
* BASE64(HS256Secure(LowSig))
*
* */


@SpringBootApplication
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }

}
