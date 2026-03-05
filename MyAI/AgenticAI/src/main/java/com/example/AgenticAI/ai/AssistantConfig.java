package com.example.AgenticAI.ai;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssistantConfig {

    @Bean
    public Assistant assistant(OpenAiChatModel model, CrudTools tools) {
        return AiServices.builder(Assistant.class)
                .chatModel(model)     // ✔ NOW valid
                .tools(tools)
                .build();
    }
}