package com.example.demo.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.UserEntity;

@Service
public class UserService {

    private static final String EXISTING_EMAIL = "test@test.com";
    private static final String ANOTHER_EMAIL = "next@test.com";


    public Optional<UserEntity> findByEmail(String email){
        if(EXISTING_EMAIL.equalsIgnoreCase(email)){
            var user = new UserEntity();
            //Sanki database'den bir entity çekmiş gibi davrandık
            user.setId(1L);
            user.setEmail(EXISTING_EMAIL);
            user.setPassword("$2a$12$oeYW8P1RfpWrldJyZdlmhuKVwTKgReK/KPEozZI9hg9TRna1ZdJR2"); // test
            user.setRole("ADMIN");
            user.setExtraInfo("my admin");
    
            return Optional.of(user);
        } else if(ANOTHER_EMAIL.equalsIgnoreCase(email)){
            var user = new UserEntity();
            //Sanki database'den bir entity çekmiş gibi davrandık
            user.setId(99L);
            user.setEmail(ANOTHER_EMAIL);
            user.setPassword("$2a$12$oeYW8P1RfpWrldJyZdlmhuKVwTKgReK/KPEozZI9hg9TRna1ZdJR2"); // test
            user.setRole("ROLE_USER");
            user.setExtraInfo("my simple user");
    
            return Optional.of(user);
        }

        return Optional.empty();

     
        
    }
}
