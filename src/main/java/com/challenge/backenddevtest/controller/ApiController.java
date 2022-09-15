package com.challenge.backenddevtest.controller;

import com.challenge.backenddevtest.response.NotFoundResponse;
import com.challenge.backenddevtest.model.ProductDetail;
import com.challenge.backenddevtest.service.SimilarProductService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final SimilarProductService similarProductService;

    @GetMapping("/product/{productId}/similar")
    public ResponseEntity<Set<ProductDetail>> getSimilarProducts(@PathVariable String productId) {
        return ResponseEntity.ok(similarProductService.getSimilarProducts(productId));
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<NotFoundResponse> returnNotFoundProduct() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(NotFoundResponse.builder().description("Product Not Found").build());
    }

}
