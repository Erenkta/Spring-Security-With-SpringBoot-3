package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequiredArgsConstructor // Bu ne araştır
public class HelloController {

    @GetMapping("/")
    public String greeting() {
        return new String("Hello World");
    }
    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        return "if you see this then you logged in as user " + principal.getEmail() + " /  USER ID : " + principal.getUserId() ;
    }
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "you'll see this if the user : "
        +principal.getEmail() + " " 
        + principal.getUserId() +"is admin";
    }
    
    
    
    
}
