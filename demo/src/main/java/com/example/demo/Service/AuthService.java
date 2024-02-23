package com.example.demo.Service;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.LoginResponse;
import com.example.demo.Security.JwtIssuer;
import com.example.demo.Security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {


     
    private final JwtIssuer jwtIssuer;
    private  final AuthenticationManager authenticationManager; // kullanıcı creds'leri girince spring security'e düşüyor.
    //biz de security'e düşen creds'leri burdan alıyoruz
    
    public LoginResponse attemptLogin(String email,String password){
                var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email,password) // email ve password ile token oluşturduk
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal(); 
        //getPrincipal() object dönüyordu bunu ondan dolayı cast ettik

        var roles = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        var token = jwtIssuer.issue(principal.getUserId(),principal.getEmail(),roles);
        return LoginResponse.builder().accessToken(token).build();
    }
    
}
