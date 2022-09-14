package com.challenge.backenddevtest.config;

import com.challenge.backenddevtest.client.ProductApiClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = ProductApiClient.class)
public class ProductApiClientConfig {
}
