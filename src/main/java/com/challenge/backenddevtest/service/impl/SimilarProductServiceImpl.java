package com.challenge.backenddevtest.service.impl;

import com.challenge.backenddevtest.client.ProductApiClient;
import com.challenge.backenddevtest.model.ProductDetail;
import com.challenge.backenddevtest.service.SimilarProductService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimilarProductServiceImpl implements SimilarProductService {

    private final ProductApiClient productApiClient;

    @Override
    public Set<ProductDetail> getSimilarProducts(String productId) {
        log.debug("System is going to retrieve all details about similar products to product with id={}", productId);
        return Optional.ofNullable(getSimilarProductIdsFromClient(productId))
                .orElse(new HashSet<>())
                .stream()
                .map(this::getProductDetailFromClient)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<String> getSimilarProductIdsFromClient(String productId) {
        log.debug("Retrieving the identification numbers for all similar products to product with id={}", productId);
        Set<String> similarProducts = null;
        try {
            similarProducts = productApiClient.getSimilarProducts(productId);
        } catch (FeignException.NotFound e) {
            throw e;
        } catch (FeignException e) {
            log.warn("Product API client threw the next error: {}", e.getMessage());
        }
        return similarProducts;
    }

    private ProductDetail getProductDetailFromClient(String productId) {
        log.debug("Retrieving the details about product with id={}", productId);
        ProductDetail productDetail = null;
        try {
            productDetail = productApiClient.getProductDetail(productId);
        } catch (FeignException e) {
            log.warn("There was an issue with Product API client and it returned an error: {}", e.getMessage());
        }
        return productDetail;
    }
}
