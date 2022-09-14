package com.challenge.backenddevtest.service;

import com.challenge.backenddevtest.model.ProductDetail;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface SimilarProductService {
    Set<ProductDetail> getSimilarProducts(String productId);
}
