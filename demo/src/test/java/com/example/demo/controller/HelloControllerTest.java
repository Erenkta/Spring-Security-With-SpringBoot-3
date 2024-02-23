package com.example.demo.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // Gerçek requestler atıcak
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void  anyoneCanViewPublicEndpointGreeting() throws Exception{

       ResultActions response = mockMvc.perform(get("/")
        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
        .andExpect(content().string(Matchers.containsStringIgnoringCase("Hello")));


    }

    @Test
    void notLoggedInUserShouldntSeeThisSecuredEndPoint() throws Exception {
        ResultActions response = mockMvc.perform(get("/secured")
        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isUnauthorized()); //Normalde hata verir çünkü 403 dönüyor biz de ondan dolayı
        //UnauthorizedHandler oluşturup importlar yaptık ve bunu config'e ekledik
        //O classta basitce security'nin kullandığı handler'i import edip 403 değil de 401 yollasın dedik
    }

    @Test
    @WithMockUser
    void LoggedInUserShouldSeeThisSecuredEndPoint() throws Exception {
        ResultActions response = mockMvc.perform(get("/secured")
        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk()) // Fakat bu kullanıcının LoggedIn durumda olduğunu nasıl anlayacak ?
        //Bunun için spring Security bize bir takım utils'ler sunuyor   
        .andExpect(content().string(Matchers.containsStringIgnoringCase("USER ID : 1")));
    }
    @Test
    void notLoggedInUserShouldntSeeThisAdminEndPoint() throws Exception {
        ResultActions response = mockMvc.perform(get("/admin")
        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isUnauthorized()); //Normalde hata verir çünkü 403 dönüyor biz de ondan dolayı
        //UnauthorizedHandler oluşturup importlar yaptık ve bunu config'e ekledik
        //O classta basitce security'nin kullandığı handler'i import edip 403 değil de 401 yollasın dedik
    }
    @Test
    @WithMockUser
    void simpleUserShouldntSeeAdminEndPoint() throws Exception{
        ResultActions response = mockMvc.perform(get("/admin")
        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isForbidden());
    }
    @Test
    @WithAdminUser
    void AdminShouldSeeAdminEndPoint() throws Exception{
        ResultActions response = mockMvc.perform(get("/admin")
        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
        .andExpect(content().string(Matchers.containsStringIgnoringCase("is admin")));
    }



 
}
