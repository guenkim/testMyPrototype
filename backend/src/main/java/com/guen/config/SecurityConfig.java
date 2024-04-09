package com.guen.config;

import com.guen.jwt.config.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableMethodSecurity //method 기반 security 적용
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint entryPoint;

    private static final String[] ALLOWED_URIS = {"/", "/swagger-ui/**", "/v3/**","/api/sign-up", "/api/sign-in"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return
                //spring security에 cors 허용
                http.cors()
                .and()
                .csrf().disable()
                // H2 콘솔 사용을 위한 설정
                .headers(headers -> headers.frameOptions().sameOrigin())
                .authorizeHttpRequests(request ->
                        // requestMatchers의 인자로 전달된 url은 모두에게 허용
                        request.requestMatchers(ALLOWED_URIS).permitAll()
                                // 그 외의 모든 요청은 인증 필요
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        // 쿠키,세션을 사용하지 않으므로 STATELESS 설정
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //jwt를 기반으로 한 필터를 적용
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)

                //@ControllerAdvice에서 예외를 처리하도 설정
//                .exceptionHandling(handler -> handler
//                        .defaultAuthenticationEntryPointFor(entryPoint, new AntPathRequestMatcher("/**"))
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND))
//)
                        //@ControllerAdvice에서 예외를 처리하도 설정
                        .exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint))
                .build();
    }


}

