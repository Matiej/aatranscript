package com.emat.openAi.global;

import org.springframework.beans.factory.annotation.Value;

public class OpeApiParams {

    @Value("${openAI-key}")
    private String openAIKey;

    public String getOpenAIKey() {
        System.out.println("XXXXXXXXXXX - " + openAIKey + " - XXXXXXXXXXXX");

        return openAIKey;
    }

}
