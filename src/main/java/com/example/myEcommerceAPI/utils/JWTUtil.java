package com.example.myEcommerceAPI.utils;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.myEcommerceAPI.security.SecurityConstants;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {
    
    private static final String SECRET_KEY = SecurityConstants.SECRET_KEY;
    HttpServletRequest request;

        public Long extractUserId(HttpServletRequest request) {
            String token = getTokenFromRequest(request);
            if (token != null) {
                token = token.replace("Bearer ", "");
                DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                        .build()
                        .verify(token.replace(SecurityConstants.BEARER, ""));
                return jwt.getClaim("user_id").asLong();
            }
            return null;
        }
    
        public String extractUsername(String token) {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(token.replace(SecurityConstants.BEARER, ""));
            return jwt.getSubject();
        }

        private String getTokenFromRequest(HttpServletRequest request) {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                return header.replace("Bearer ", "");
            }
            return null;
        }
    }


