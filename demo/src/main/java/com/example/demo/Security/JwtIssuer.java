package com.example.demo.Security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    
    public final JwtProperties properties;

    public String issue(long userId,String email,List<String> roles){
        return JWT.create()
        .withSubject(String.valueOf(userId)) // şifrenin kime ait olduğu
        .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS))) // Token'in etkisiz olma zamanı
        .withClaim("e",email)//şifreye dahil edilecekler
        .withClaim("a",roles)
        .sign(Algorithm.HMAC256(properties.getSecretKey())); // Gizli anahtar //Bunu properties şeklinde verdik doğru yolu bu
        // Properties nasıl oluşturulur için JwtProperties class'ına daha sonra da application.yaml'a bak
        //application.yaml'da hata veriyorsa spring-boot-configuration-processor bunu import et maven'dan
    }
}
