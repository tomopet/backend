package com.nap.tomopet.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //1. Request Header에서 JWT 토큰 추출
        String token = resolveToken(request);
        //2. 토큰 유효성 검사
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            //토큰이 유효하면 토큰에서 유저 아이디 꺼내기
            String username = jwtTokenProvider.getUsername(token);
            //DB에서 유저 정보 조회
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //스프링 시큐리티 인증 객체
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities() );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //3. 다음 필터로 요청 넘김
        filterChain.doFilter(request, response);
    }

    //HTTP 요청 헤더에서 파싱하는 메서드(슬 주석 쓰기 귀찮은데)
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearere")) {
            return bearerToken.substring(7);
        }
        return null;

    }

}
