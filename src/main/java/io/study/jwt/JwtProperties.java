package io.study.jwt;

public interface JwtProperties {
    String SECRET = "SECURITY";
    int PXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization ";
}
