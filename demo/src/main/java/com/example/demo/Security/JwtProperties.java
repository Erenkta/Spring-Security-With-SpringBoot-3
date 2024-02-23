package com.example.demo.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    /**
     * JWT için secret-key 
     */

     //yukarıda yazdığımz şey application.yaml için açıklama.Eğer gidip üstüne bakarsan görürsün
    private String secretKey;
}
