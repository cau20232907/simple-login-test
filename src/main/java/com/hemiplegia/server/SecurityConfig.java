package com.hemiplegia.server;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(login -> login
                        .loginProcessingUrl("/signin")
                        .failureHandler(((request, response, exception) -> {
                            String errorMessage;
                            if (exception instanceof BadCredentialsException ||
                                    exception instanceof UsernameNotFoundException ||
                                    exception instanceof DisabledException ||
                                    exception instanceof LockedException) {
                                errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
                            } else if (exception instanceof InternalAuthenticationServiceException) {
                                errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요. ";
                            } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                                errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
                            } else {
                                errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
                            }
                            request.setAttribute("errormessage", errorMessage);
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        })))
                .csrf(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
