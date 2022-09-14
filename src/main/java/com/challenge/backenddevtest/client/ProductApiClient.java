package com.challenge.backenddevtest.client;

import com.challenge.backenddevtest.config.ProductApiClientConfig;
import com.challenge.backenddevtest.model.ProductDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@FeignClient(name = "ProductApiClient",
        url = "${product-api-client.baseUrl")
public interface ProductApiClient {

    @GetMapping("${product-api-client.getSimilarProductUrl}")
    ResponseEntity<Set<String>> getSimilarProducts(@PathVariable String productId);

    @GetMapping("${product-api-client.getProductDetail")
    ResponseEntity<ProductDetail> getProductDetail(@PathVariable String productId);
}
