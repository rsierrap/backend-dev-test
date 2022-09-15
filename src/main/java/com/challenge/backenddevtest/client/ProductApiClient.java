package com.challenge.backenddevtest.client;

import com.challenge.backenddevtest.model.ProductDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Set;

@FeignClient(name = "ProductApiClient",
        url = "${product.api.baseUrl}")
public interface ProductApiClient {

    @GetMapping("${product.api.similarProductsUrl}")
    Set<String> getSimilarProducts(@PathVariable String productId);

    @GetMapping("${product.api.productDetailUrl}")
    ProductDetail getProductDetail(@PathVariable String productId);
}
