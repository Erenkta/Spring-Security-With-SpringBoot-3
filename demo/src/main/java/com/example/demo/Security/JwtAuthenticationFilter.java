package com.example.demo.Security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        extractTokenFromRequest(request)
                .map(jwtDecoder::decode) // str-> jwtDecoder.decode(str)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new) // UserPrincipalAuthenticationToken oluşturuyor ve bir önceki mapten gelen User Principal'i veriyor
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication)); // yukarıda oluşan nesneyi de contexte yolladık

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){ // eğer token bir text ise ve Bearer ile başlıyorsa
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
    
}
