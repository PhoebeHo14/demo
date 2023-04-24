package com.example.demo.constant;

public class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/auth/login";

    public static final String JWT_SECRET_KEY = "p2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdS";

    public static final String TOKEN_PREFIX = "Bearer ";

    // Authorization request header
    public static final String TOKEN_HEADER = "Authorization";

    // token type
    public static final String TOKEN_TYPE = "JWT";

    // token expireTime = 2 hours
    public static final long EXPIRATION_TIME = 60 * 60 * 2L;

}
