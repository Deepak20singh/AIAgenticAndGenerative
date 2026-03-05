package com.example.DeepakAi.rag;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestService {

    private final EmbeddingModel embeddingModel;
    private final InMemoryStore memory;

    public void ingestPersona() throws Exception {
        // NOTE: path ko apne project structure ke hisaab se adjust kar sakte ho
        Path personaPath = Path.of("src/main/resources/memories/deepak_persona.txt");
        String text = Files.readString(personaPath);

        // 🔹 Chunk: 300 chars with 50-char overlap (tune as you like)
        List<TextSegment> segments = SimpleChunker.chunk(text, 300, 50);

        // 🔹 Embed & store
        for (TextSegment seg : segments) {
            var emb = embeddingModel.embed(seg).content();
            memory.save(emb, seg);
        }

        System.out.println("✅ Deepak personality loaded! Chunks: " + segments.size());
    }
}