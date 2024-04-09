package com.guen.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guen.program.shop.model.dto.request.ReqItemDto;
import com.guen.program.shop.model.enumclass.BatType;
import com.guen.program.shop.model.enumclass.OrderStatus;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OrderControllerTest {

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
    public void 주문생성() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .header("Authorization", accessToken)
                        .param("crewId","1")
                        .param("itemId","52")
                        .param("count","77777")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void 주문목록반환() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
                        .header("Authorization", accessToken)
                        .param("crewId","1")
                        .param("orderStatus", OrderStatus.ORDER.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.order.[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order.[1].id").value("52"));
    }


    @Test
    public void 주문취소() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/152")
                        .header("Authorization", accessToken)
                        .param("orderId","152")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
