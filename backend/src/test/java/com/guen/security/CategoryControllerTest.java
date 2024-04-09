package com.guen.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guen.program.shop.model.dto.request.ReqCategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String accessToken;
    @BeforeEach
    public void before(){
        accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MzM4Yjk0MC1jOWIwLTQ1ZTktYWNkOS00YTVjOTVmYjdhYjA6VVNFUiIsImlzcyI6ImdldW4iLCJpYXQiOjE3MDk2OTA1OTQsImV4cCI6MTcwOTY5MjM5NH0.xQXTuxksmGBqBbTz8SQbjEthmeuroEqzYq7xmVIRqTkk6XmbV_l7anC7i-5JA3cr9u50vAeh3z2GzEXqrUSz1w";
    }

    @Test
    public void 카테고리생성() throws Exception{
        ReqCategoryDto newCategory = new ReqCategoryDto("동남아주식");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void 카테고리목록반환() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("중국주식"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("동남아주식"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("주식"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("코인"));
    }
}
