package com.guen.jwt.config;

import com.guen.program.todo.exception.PathVariableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        try {
            resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
        }catch (Exception e){
            request.setAttribute("exception", new PathVariableException("잘못되 접근"));
            resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
        }
    }
}
