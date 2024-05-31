package com.emat.openAi.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final ChatClient client;

    public OpenAiServiceImpl(ChatClient client) {
        this.client = client;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = client.call(prompt);
        Generation result = response.getResult();
        AssistantMessage output = result.getOutput();
        return output.getContent();    }
}
