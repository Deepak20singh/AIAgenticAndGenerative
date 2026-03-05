package com.example.DeepakAi.rag;



import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.stereotype.Component;

@Component
public class InMemoryStore {

    public final EmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

    public void save(Embedding emb, TextSegment seg) {
        store.add(emb, seg);
    }
}