package com.example.DeepakAi.rag;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaRagService {

    private final EmbeddingModel embeddingModel;
    private final OpenAiChatModel llm;
    private final InMemoryStore memory;

    // MAIN ENTRY POINT
    public String answer(String userMsg) {

        boolean wantsGita =
                userMsg.toLowerCase().contains("advice") ||
                        userMsg.toLowerCase().contains("salah") ||
                        userMsg.toLowerCase().contains("guidance") ||
                        userMsg.toLowerCase().contains("gyaan") ||
                        userMsg.toLowerCase().contains("samjhao") ||
                        userMsg.toLowerCase().contains("confused") ||
                        userMsg.toLowerCase().contains("krishna");

        if (wantsGita) {
            return gitaAiResponse(userMsg);
        }

        return deepakAiResponse(userMsg);
    }

    // 🧘‍♂️ GITA MODE
    private String gitaAiResponse(String userMsg) {

        String prompt = """
                You are GITA-AI, a calm spiritual guide.

                RULES:
                - Hinglish tone
                - Peaceful, empathetic
                - No exact Bhagavad Gita shloka (copyrighted)
                - Give principle-based guidance
                - Add small “Gita principle reference”
                - Do NOT break character

                User asked:
                "%s"

                Give GITA-style answer:
                """.formatted(userMsg);

        return llm.chat(prompt);
    }

    // 😎 DEEPAK MODE
    private String deepakAiResponse(String userMsg) {

        Embedding qEmb = embeddingModel.embed(userMsg).content();

        EmbeddingSearchRequest req = EmbeddingSearchRequest.builder()
                .queryEmbedding(qEmb)
                .maxResults(5)
                .build();

        EmbeddingSearchResult<TextSegment> result = memory.store.search(req);

        String traits = result.matches().stream()
                .map(m -> m.embedded().text())
                .collect(Collectors.joining("\n- ", "- ", ""));

        String prompt = """
                You are DEEPAK-AI.

                Style:
                - Hinglish
                - Friendly + short
                - Use phrases: "bhai", "mast", "simple sa funda"
                - Tiny example-based explanations
                - Never formal

                Personality memories:
                %s

                User: "%s"

                Reply as Deepak-AI:
                """.formatted(traits, userMsg);

        return llm.chat(prompt);
    }
}