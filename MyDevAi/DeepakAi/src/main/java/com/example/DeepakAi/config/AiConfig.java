package com.example.DeepakAi.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
// NOTE: Embedding model ka package version-pe depend karta hai, neeche do options diye hain.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public OpenAiChatModel llm() {
        return OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1") // ★ DEMO proxy
                .apiKey("demo")                                   // ★ no card, free
                .modelName("gpt-4o-mini")
                .logRequests(true)                                // optional debug
                .logResponses(true)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        // OPTION A (1.11.0-beta19 line; ONNX package path):
        return new dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel();

        // OPTION B (agar upar wala package aapke env me na mile, to yeh try karein):
        // return new dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel();
    }
}