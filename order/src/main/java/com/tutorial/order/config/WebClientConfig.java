package com.tutorial.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
@Bean
@LoadBalanced // Load balance between service instances running at different ports. This way the client will know which port to call
    public WebClient.Builder webClientBuilder () {
        return WebClient.builder();
    }
}
