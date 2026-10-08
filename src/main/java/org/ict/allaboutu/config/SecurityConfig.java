package org.ict.allaboutu.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.config.filter.JwtAuthenticationFilter;
import org.ict.allaboutu.member.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;  // HttpMethod를 임포트 추가
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> anyList = List.of("/auth/**", "/index.html", "/signup/**", "/css/**", "/js/**", "/img/**", "/h2-console/**", "/favicon.ico", "/login/**", "/logout", "/api/**", "/assets/**",
                "/notices/search", "/notices/imp", "/notices/image/{renameFileName}", "/notices/download/{renameFileName}", "/notices/detail/{noticeNum}", "/boards/search", "/boards/image/{imageName}",
                "/", "/member/{userId}", "/user_profile/**", "/member/image/{imageName}", "/style/image/{imageName}", "/personal/image/{imageName}", "/face/image/{imageName}", "/cody/image/{imageName}", "/member/verifyCode", "/cody/**", "/style/**");
        List<String> userOnlyList = List.of("/personal/**", "/boards/{boardNum}", "/member/{userId}", "/", "/face/**", "/myPage/**", "/upload/**");
        List<String> adminOnlyList = List.of("/reports/{reportNum}", "/reports/**", "/admin/get");

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/boards/rank").permitAll()
                                .requestMatchers(HttpMethod.GET, "/boards/{boardNum}", "/boards/{boardNum}/likes/{userId}", "/boards/{boardNum}/comments").authenticated()
                                .requestMatchers(HttpMethod.POST, "/boards", "/boards/{boardNum}/reports", "/boards/{boardNum}/likes", "/boards/{boardNum}/comments").authenticated()
                                .requestMatchers(HttpMethod.POST, "/notices").hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/notices/{noticeNum}").hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}").access(new WebExpressionAuthorizationManager("@accessService.isBoardAuthor(authentication, #boardNum)"))
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}/likes/{userId}").access(new WebExpressionAuthorizationManager("@accessService.isLikeOwner(authentication, #boardNum, #userId)"))
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}/comments/{commentNum}").access(new WebExpressionAuthorizationManager("@accessService.isCommentAuthor(authentication, #boardNum, #commentNum)"))
                                .requestMatchers(HttpMethod.PATCH, "/boards/{boardNum}").access(new WebExpressionAuthorizationManager("@accessService.isBoardAuthor(authentication, #boardNum)"))
                                .requestMatchers(HttpMethod.PATCH, "/boards/{boardNum}/comments/{commentNum}").access(new WebExpressionAuthorizationManager("@accessService.isCommentAuthor(authentication, #boardNum, #commentNum)"))
                                .requestMatchers(HttpMethod.PATCH, "/notices/{noticeNum}").hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(anyList.toArray(new String[0])).permitAll()
                                .requestMatchers(userOnlyList.toArray(new String[0])).hasAnyAuthority(UserRole.USER.name())
                                .requestMatchers(adminOnlyList.toArray(new String[0])).hasAuthority(UserRole.ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(
                                    "{\"code\":\"UNAUTHORIZED\"}"
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(
                                    "{\"code\":\"FORBIDDEN\"}"
                            );
                        })
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfig -> {
                    logoutConfig
                            .logoutUrl("/auth/logout")
                            .addLogoutHandler(logoutService);
                });

        return http.build();

    }

}
