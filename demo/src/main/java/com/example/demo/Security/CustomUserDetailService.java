package com.example.demo.Security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.Service.UserService;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService { 
    // Spring bu service ile belirli bir bilgiye göre user'ı getirme işlemini yapıyor
    // Biz de bu class'ı kullanarak bu işlemi yapacağız

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Burada bir UserDetails class'ı döneceğiz. 
    //Biz de bu class'ı UserPrincipal'a implemente etmiştik. Yani onu dönmeliyiz

        var user = userService.findByEmail(username).orElseThrow();

        return UserPrincipal.builder()
        .userId(user.getId())
        .email(user.getEmail())
        .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
        .password(user.getPassword())
        .build();

    }
    // bu işlem bittikten sonra bu service'i security ile bağlamamız lazım
    
}
