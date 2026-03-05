package com.example.AgenticAI.rag;


import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngestionService {

    private final EmbeddingModel embeddingModel;
    private final InMemoryVectorStore store;

    public IngestionService(EmbeddingModel embeddingModel, InMemoryVectorStore store) {
        this.embeddingModel = embeddingModel;
        this.store = store;
    }

    public int ingest(String docId, String text, Map<String, Object> meta) {
        List<String> chunks = chunk(text, 900, 150);
        int count = 0;
        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            Embedding emb = embeddingModel.embed(chunk).content();
            store.insert(docId, i, chunk, meta, emb.vector());
            count++;
        }
        return count;
    }

    private List<String> chunk(String text, int size, int overlap) {
        List<String> out = new ArrayList<>();
        if (text == null || text.isBlank()) return out;
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(text.length(), start + size);
            out.add(text.substring(start, end));
            if (end == text.length()) break;
            start = Math.max(0, end - overlap);
        }
        return out;
    }
}