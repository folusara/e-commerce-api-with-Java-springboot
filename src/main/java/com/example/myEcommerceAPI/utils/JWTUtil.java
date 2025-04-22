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
    
    HttpServletRequest request;
        public static Long extractUserId(HttpServletRequest request) {
            String token = getTokenFromRequest(request);
            if (token != null) {
                token = token.replace("Bearer ", "");
                DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                        .build()
                        .verify(token.replace(SecurityConstants.BEARER, ""));
                return jwt.getClaim("user_id").asLong();
            }
            return null;
        }
    
        public static String extractUsername(String token) {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                    .build()
                    .verify(token.replace(SecurityConstants.BEARER, ""));
            return jwt.getSubject();
        }

        private static String getTokenFromRequest(HttpServletRequest request) {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                return header.replace("Bearer ", "");
            }
            return null;
        }
    }


