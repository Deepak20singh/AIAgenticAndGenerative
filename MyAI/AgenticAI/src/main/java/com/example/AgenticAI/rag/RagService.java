package com.example.AgenticAI.rag;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final EmbeddingModel embeddingModel;
    private final InMemoryVectorStore store;
    private final OpenAiChatModel chatModel;  // <--- Direct OpenAiChatModel

    public RagService(EmbeddingModel embeddingModel, InMemoryVectorStore store, OpenAiChatModel chatModel) {
        this.embeddingModel = embeddingModel;
        this.store = store;
        this.chatModel = chatModel;
    }

    public String buildContext(String question, int topK) {
        Embedding q = embeddingModel.embed(question).content();
        List<InMemoryVectorStore.Item> hits = store.topK(q.vector(), topK);
        return hits.stream().map(h -> "- " + h.content()).collect(Collectors.joining("\n"));
    }

    public String answerWithRag(String question, int topK) {
        String ctx = buildContext(question, topK);
        String prompt = """
            Tum ek helpful assistant ho. Bas neeche diye gaye CONTEXT par hi rely karo.
            Agar context me answer clear na ho to bolo "Mujhe context me nahi mila."

            QUESTION:
            %s

            CONTEXT:
            %s

            Answer short & Hinglish me do.
            """.formatted(question, ctx);

        return chatModel.chat(prompt);
    }
}