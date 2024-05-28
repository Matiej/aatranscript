package com.emat.openAi.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiAppConfiguration {

    @Bean
    public OpeApiParams getOpeApiParams() {
        System.out.println("Starting OpeApiParams XXXXXXXXXXXXXXXXXXXXXXX");
        return new OpeApiParams();
    }
}
