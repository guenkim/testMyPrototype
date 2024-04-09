package com.guen.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    @BeforeEach
    public void before(){
        accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MzM4Yjk0MC1jOWIwLTQ1ZTktYWNkOS00YTVjOTVmYjdhYjA6VVNFUiIsImlzcyI6ImdldW4iLCJpYXQiOjE3MTI2MTk3OTYsImV4cCI6MTcxMjYyMTU5Nn0.R1SZGLxtJ5xc85bM0zCCBYpHbnT6BMnzTX7cHjKYOFbmL4OvfWhPZ6S_621BJpumrZ49I9QrAQ44qsxiRBB9Kg";
    }
    @Test
    @WithCustomMockUser
    public void 가짜user적용() throws Exception{
        mockMvc.perform(get("/api/member")
                        .header("Authorization", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void todo목록반환() throws Exception{
        mockMvc.perform(get("/api/todos")
                        .param("subject", "제목")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "id,desc,subject,desc")
                        .header("Authorization", accessToken)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    public void todo생성() throws Exception{
        // todoReq 객체 준비
        TodoReq todoReq = TodoReq.builder()
                .subject("테스트 제목3")
                .body("테스트 내용3")
                .completed(Complete.FALSE)
                .build();

        File file = new File("C:\\Practice2024\\jpa\\springjpa1-v20231204\\2. 도메인 분석 설계.pdf");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] content = StreamUtils.copyToByteArray(inputStream);

        File file2 = new File("C:\\Practice2024\\jpa\\springjpa1-v20231204\\3. 애플리케이션 구현 준비.pdf");
        FileInputStream inputStream2 = new FileInputStream(file2);
        byte[] content2 = StreamUtils.copyToByteArray(inputStream2);

        MockMultipartFile filePart = new MockMultipartFile("files", file.getName(), "application/pdf", content);
        MockMultipartFile filePart2 = new MockMultipartFile("files", file2.getName(), "application/pdf", content2);
        MockMultipartFile todoReqPart = new MockMultipartFile("todoReq", "", "application/json", objectMapper.writeValueAsBytes(todoReq));

        mockMvc.perform(multipart("/api/todos")
                        .file(todoReqPart)
                        .file(filePart)
                        .file(filePart2)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNoContent()); // 204 상태 코드 검증
    }

    @Test
    public void todo반환() throws Exception{
        mockMvc.perform(get("/api/todos/252")
                        .header("Authorization", accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void todo수정() throws Exception{
        // todoReq 객체 준비
        TodoReq todoReq = TodoReq.builder()
                .subject("테스트 제목3 수정")
                .body("테스트 내용3 수정")
                .completed(Complete.TRUE)
                .build();

        File file = new File("C:\\Practice2024\\jpa\\springjpa1-v20231204\\4. 회원 도메인 개발.pdf");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] content = StreamUtils.copyToByteArray(inputStream);

        File file2 = new File("C:\\Practice2024\\jpa\\springjpa1-v20231204\\7. 웹 계층 개발.pdf");
        FileInputStream inputStream2 = new FileInputStream(file2);
        byte[] content2 = StreamUtils.copyToByteArray(inputStream2);

        MockMultipartFile filePart = new MockMultipartFile("files", file.getName(), "application/pdf", content);
        MockMultipartFile filePart2 = new MockMultipartFile("files", file2.getName(), "application/pdf", content2);
        MockMultipartFile todoReqPart = new MockMultipartFile("todoReq", "", "application/json", objectMapper.writeValueAsBytes(todoReq));

        mockMvc.perform(multipart(HttpMethod.PUT,"/api/todos/252")
                        .file(todoReqPart)
                        .file(filePart)
                        .file(filePart2)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isNoContent()); // 204 상태 코드 검증
    }

    @Test
    public void todo삭제() throws Exception{
        mockMvc.perform(delete("/api/todos/252")
                        .header("Authorization", accessToken))
                .andDo(print())
                .andExpect(status().isNoContent()); // 204 상태 코드
    }

    @Test
    public void todo완료여부설정() throws Exception{
        mockMvc.perform(patch("/api/todos/202/TRUE")
                        .header("Authorization", accessToken))
                .andDo(print())
                .andExpect(status().isNoContent()); // 204 상태 코드
    }
}
