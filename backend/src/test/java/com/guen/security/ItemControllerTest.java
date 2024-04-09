package com.guen.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guen.program.shop.model.dto.request.ReqItemDto;
import com.guen.program.shop.model.enumclass.BatType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    @BeforeEach
    public void before(){
        accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MzM4Yjk0MC1jOWIwLTQ1ZTktYWNkOS00YTVjOTVmYjdhYjA6VVNFUiIsImlzcyI6ImdldW4iLCJpYXQiOjE3MDk2OTQ3NTIsImV4cCI6MTcwOTY5NjU1Mn0.ZBHW6hz5aPpr4K_cWEFatbnIeAmGLZ5XSHqv82vRz_krhTzRx6KXZigiXI_7Mza_yOO3D2AKR-FcCUxLBK-NOw";
    }

    @Test
    public void 아이템생성() throws Exception{
        ReqItemDto reqItemDto = new ReqItemDto("바이두*1000", 10000000, 99999999, BatType.STOCK, "BIDU*1000", "", List.of(new ReqItemDto.Category(1L)));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/item")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqItemDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void 아이템목록반환() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("바이두*300"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("바이두"));
    }

    @Test
    public void 아이템반환() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/52")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("바이두*300"));
    }

    @Test
    public void 물품수정() throws Exception{
        ReqItemDto reqItemDto = new ReqItemDto("바이두*1500", 10000000, 99999999, BatType.STOCK, "BIDU*1000", "", List.of(new ReqItemDto.Category(1L)));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/items/52")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqItemDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void 아이템삭제() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/items/252")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
