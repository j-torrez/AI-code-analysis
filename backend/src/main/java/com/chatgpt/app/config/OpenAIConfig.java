package com.chatgpt.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  // Indicates that this class contains Spring configuration
public class OpenAIConfig {

    @Value("${openai.api.key}")  // Injects the OpenAI API key from application properties
    String openaiApiKey;

    @Bean  // Defines a Spring bean for RestTemplate
    public RestTemplate template() {
        RestTemplate restTemplate = new RestTemplate();  // Creates a new instance of RestTemplate
        
        // Adds an interceptor to the RestTemplate to include the Authorization header
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);  // Adds the API key to the Authorization header
            return execution.execute(request, body);  // Executes the request with the added header
        });
        
        return restTemplate;  // Returns the configured RestTemplate instance
    }
}


