package com.example.myEcommerceAPI.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)";
    public static final int TOKEN_EXPIRATION = 7200000;
    public static final String BEARER = "Bearer "; 
    public static final String AUTHORIZATION = "Authorization"; 
    public static final String REGISTER_PATH = "/user/register"; 
    public static final String FORGOT_PASSWORD_PATH = "/user/forgotPassword";
    public static final String RESET_PASSWORD_PATH = "/user/reset-password";
    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String SWAGGER_UI_HTML = "swagger-ui.html";
}
