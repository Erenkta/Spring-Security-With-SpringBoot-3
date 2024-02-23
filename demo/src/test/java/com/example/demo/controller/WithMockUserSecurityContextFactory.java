package com.example.demo.controller;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.example.demo.Security.UserPrincipal;
import com.example.demo.Security.UserPrincipalAuthenticationToken;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        var authorities = Arrays.stream(annotation.authorities()).map(SimpleGrantedAuthority::new).toList();

        var principle =  UserPrincipal.builder().userId(annotation.userId()).email("fake@email.com").authorities(authorities).build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserPrincipalAuthenticationToken(principle));

        return context;
    }
    
}
