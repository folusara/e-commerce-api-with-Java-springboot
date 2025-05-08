package com.example.myEcommerceAPI.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            System.err.println(request.getMethod());
            System.err.println(request.getRequestURI());
            chain.doFilter(request, response);
        }
        catch (jakarta.persistence.EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // response.getWriter().write("entity not found: " + e.getMessage());
            response.setContentType("application/json");
            String json = new ObjectMapper().writeValueAsString(Map.of("message", "entity not found" + e.getMessage()));
            response.getWriter().write(json);
        }
        catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            String json = new ObjectMapper().writeValueAsString(Map.of("message", "jwt error:" + e.getMessage()));
            response.getWriter().write(json);
            
        }
        catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            String json = new ObjectMapper().writeValueAsString(Map.of("message", "run time error:" +e.getMessage()));
            response.getWriter().write(json);
        }
    }
}
