package com.example.demo.Security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

//Bu class bize verilen token'i decode etmek için

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    public DecodedJWT decode(String token){
        //Jwt.decode'ı burada kullanma çünkü signature yani secret key'i doğrulamadan yapıyor ve biri taklit edebilir
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
            .build()
            .verify(token);
    }
}
