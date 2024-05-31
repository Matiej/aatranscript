package com.emat.openAi.global;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiAppConfiguration {

    @Value("${spring.ai.openai.api-key}")
    private String openAIKey;

    @Bean
    public OpeApiParams getOpeApiParams() {
        System.out.println("Starting OpeApiParams XXXXXXXXXXXXXXXXXXXXXXX");
        return new OpeApiParams();
    }

    @Bean
    public ChatClient chatClient() {
        ChatModel chatModel = new OpenAiChatModel(new OpenAiApi(openAIKey));
        return ChatClient.create(chatModel);
    }
}
