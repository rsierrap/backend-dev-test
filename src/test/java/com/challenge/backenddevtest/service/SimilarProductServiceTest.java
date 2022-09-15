package com.challenge.backenddevtest.service;

import com.challenge.backenddevtest.client.ProductApiClient;
import com.challenge.backenddevtest.model.ProductDetail;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
class SimilarProductServiceTest {

    @Autowired
    private SimilarProductService similarProductService;

    @MockBean
    private ProductApiClient productApiClientMock;

    @Test
    void getProductDetailFromClientTest() {
        ProductDetail productDetail = ProductDetail.builder()
                .id("product-detail-id")
                .name("product-detail-name")
                .price(0.99)
                .availability(true)
                .build();
        Set<String> productDetailSet = new HashSet<>(Collections.singleton("product-detail-id"));
        given(productApiClientMock.getProductDetail(any())).willReturn(productDetail);
        given(productApiClientMock.getSimilarProducts(any())).willReturn(productDetailSet);

        Set<ProductDetail> retrievedProductDetails = similarProductService.getSimilarProducts("product-detail-id-similar");
        assertNotNull(retrievedProductDetails);
        assertFalse(retrievedProductDetails.isEmpty());
        assertEquals(1, retrievedProductDetails.size());

        ProductDetail retrievedProductDetail = retrievedProductDetails.iterator().next();
        assertNotNull(retrievedProductDetail);
        assertEquals("product-detail-id", retrievedProductDetail.getId());
        assertEquals("product-detail-name", retrievedProductDetail.getName());
        assertEquals(0.99, retrievedProductDetail.getPrice());
        assertTrue(retrievedProductDetail.isAvailability());
    }

    @Test
    void getProductDetailFromClientNotFoundTest() {
        given(productApiClientMock.getSimilarProducts(any())).willThrow(FeignException.NotFound.class);
        Assertions.assertThrows(FeignException.NotFound.class, () -> similarProductService.getSimilarProducts("product-detail-id-similar"));
    }

    @Test
    void getProductDetailFromClientNullSet() {
        given(productApiClientMock.getSimilarProducts(any())).willReturn(null);
        Set<ProductDetail> retrievedProductDetails = similarProductService.getSimilarProducts("product-detail-id-similar");
        assertNotNull(retrievedProductDetails);
        assertTrue(retrievedProductDetails.isEmpty());
    }
}