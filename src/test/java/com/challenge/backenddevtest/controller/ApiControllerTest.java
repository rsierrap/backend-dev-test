package com.challenge.backenddevtest.controller;

import com.challenge.backenddevtest.service.SimilarProductService;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SimilarProductService similarProductService;

    @Test
    void getSimilarProductsTest() throws Exception {
        given(similarProductService.getSimilarProducts(any())).willReturn(new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}/similar", new Random().nextInt()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSimilarProductsNotFoundTest() throws Exception {
        given(similarProductService.getSimilarProducts(any())).willThrow(FeignException.NotFound.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}/similar", new Random().nextInt()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Product Not Found"));
    }

    @Test
    void getSimilarProductsNullSetTest() throws Exception {
        given(similarProductService.getSimilarProducts(any())).willReturn(null);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}/similar", new Random().nextInt()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty());
    }
}