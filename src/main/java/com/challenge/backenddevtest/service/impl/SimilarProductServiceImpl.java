package com.challenge.backenddevtest.service.impl;

import com.challenge.backenddevtest.client.ProductApiClient;
import com.challenge.backenddevtest.model.ProductDetail;
import com.challenge.backenddevtest.service.SimilarProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        return Optional.ofNullable(getSimilarProductIdsFromClient(productId))
                .orElse(new HashSet<>())
                .stream()
                .map(this::getProductDetailFromClient)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<String> getSimilarProductIdsFromClient(String productId) {
        Set<String> similarProductIds = null;
        ResponseEntity<Set<String>> similarProductsIdsResponse = productApiClient.getSimilarProducts(productId);
        if (similarProductsIdsResponse.getStatusCode().is2xxSuccessful()) {
            similarProductIds = similarProductsIdsResponse.getBody();
        } else if (similarProductsIdsResponse.getStatusCode().is4xxClientError()) {
            log.warn("There was an issue on client side trying to retrieve the similar products ids to productId={} -> code={}", productId, similarProductsIdsResponse.getStatusCodeValue());
        } else if (similarProductsIdsResponse.getStatusCode().is5xxServerError()) {
            log.warn("There was an issue on server side trying to retrieve the similar products ids to productId={} -> code={}", productId, similarProductsIdsResponse.getStatusCodeValue());
        }
        return similarProductIds;
    }

    private ProductDetail getProductDetailFromClient(String productId) {
        ProductDetail productDetail = null;
        ResponseEntity<ProductDetail> productDetailResponse = productApiClient.getProductDetail(productId);
        if (productDetailResponse.getStatusCode().is2xxSuccessful()) {
            productDetail = productDetailResponse.getBody();
        } else if (productDetailResponse.getStatusCode().is4xxClientError()) {
            log.warn("There was an issue on client side trying to retrieve the product detail for productId={} -> code={}", productId, productDetailResponse.getStatusCodeValue());
        } else if (productDetailResponse.getStatusCode().is5xxServerError()) {
            log.warn("There was an issue on server side trying to retrieve the product detail for productId={} -> code={}", productId, productDetailResponse.getStatusCodeValue());
        }
        return productDetail;
    }
}
