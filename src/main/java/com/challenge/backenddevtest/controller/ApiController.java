package com.challenge.backenddevtest.controller;

import com.challenge.backenddevtest.model.ProductDetail;
import com.challenge.backenddevtest.service.SimilarProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

}
