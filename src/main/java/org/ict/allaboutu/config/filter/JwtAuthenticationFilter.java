package org.ict.allaboutu.config.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.config.repository.TokenRepository;
import org.ict.allaboutu.config.service.JwtService;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            // 만료된 JWT라면 ExpiredJwtException 발생시킴
            String userId = jwtService.extractUsername(jwt);
            Member member = memberRepository.findByUserId(userId);

            boolean storedTokenValid = tokenRepository.findByToken(jwt)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false);

            if (member == null
                    || !storedTokenValid
                    || !jwtService.isTokenValid(jwt, member)) {
                unauthorized(response, "INVALID_TOKEN");
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        member,
                        null,
                        List.of(new SimpleGrantedAuthority(member.getRole().name()))
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException expiredJwtException) {
            unauthorized(response, "TOKEN_EXPIRED");
            return;
        } catch (JwtException | IllegalArgumentException exception) {
            unauthorized(response, "INVALID_TOKEN");
            return;
        }

        // 다음 필터 진행
        filterChain.doFilter(request, response);
    }

    private void unauthorized(
            HttpServletResponse response,
            String code
    ) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                """
                {"code":"%s"}
                """.formatted(code)
        );
    }
}