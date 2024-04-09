package com.guen.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CrewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String accessToken;
    @BeforeEach
    public void before(){
        accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MzM4Yjk0MC1jOWIwLTQ1ZTktYWNkOS00YTVjOTVmYjdhYjA6VVNFUiIsImlzcyI6ImdldW4iLCJpYXQiOjE3MDk2OTA1OTQsImV4cCI6MTcwOTY5MjM5NH0.xQXTuxksmGBqBbTz8SQbjEthmeuroEqzYq7xmVIRqTkk6XmbV_l7anC7i-5JA3cr9u50vAeh3z2GzEXqrUSz1w";
    }

    @Test
    public void 회원생성() throws Exception{
        String requestBody = "{ \"name\": \"John Doe\", \"city\": \"New York\", \"street\": \"123 Main St\", \"zipcode\": \"10001\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/crew")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void 회원목록반환() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/crews")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("김근"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address.city").value("서울"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].address.city").value("New York"));
    }
}
