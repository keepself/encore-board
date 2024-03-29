//package com.encore.board.author.controller;
//
//import com.encore.board.author.DTO.AuthorDetailResDTO;
//import com.encore.board.author.domain.Author;
//import com.encore.board.author.service.AuthorService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
////WebMvcTest를 이용해서 Controller계층을 테스트, 모든 스프링빈을 생성하고 주입하
//@WebMvcTest(AuthorControllerTest.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthorService authorService;
//    @Test
//    @WithMockUser // security 의존성 추가 필요
//
//
//    void authorDetailTest()throws Exception{
//        AuthorDetailResDTO authorDetailResDTO = new AuthorDetailResDTO();
//        authorDetailResDTO.setName("test");
//        authorDetailResDTO.setEmail("test@test");
//        authorDetailResDTO.setPassword("1234");
//        Mockito.when(authorService.findById(1L)).thenReturn(authorDetailResDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/8/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name",authorDetailResDTO.getName(6)));
//    }
//}
