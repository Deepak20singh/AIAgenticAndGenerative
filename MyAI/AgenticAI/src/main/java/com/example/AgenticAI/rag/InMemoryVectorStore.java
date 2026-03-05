package com.example.AgenticAI.rag;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryVectorStore {

    public record Item(long id, String docId, int chunkIndex, String content, Map<String, Object> metadata, float[] vector) {}

    private final List<Item> items = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public long insert(String docId, int chunkIndex, String content, Map<String, Object> metadata, float[] vector) {
        long id = idGen.getAndIncrement();
        items.add(new Item(id, docId, chunkIndex, content, metadata == null ? Map.of() : metadata, vector));
        return id;
    }

    public List<Item> topK(float[] query, int k) {
        PriorityQueue<ItemScore> pq = new PriorityQueue<>(Comparator.comparingDouble(i -> i.score));
        for (Item it : items) {
            double score = cosineSim(query, it.vector());
            if (pq.size() < k) pq.offer(new ItemScore(it, score));
            else if (score > pq.peek().score) { pq.poll(); pq.offer(new ItemScore(it, score)); }
        }
        List<Item> out = new ArrayList<>();
        while (!pq.isEmpty()) out.add(pq.poll().item);
        Collections.reverse(out);
        return out;
    }

    private static class ItemScore {
        final Item item; final double score;
        ItemScore(Item i, double s) { item = i; score = s; }
    }

    private static double cosineSim(float[] a, float[] b) {
        double dot = 0, na = 0, nb = 0;
        int n = Math.min(a.length, b.length);
        for (int i = 0; i < n; i++) { dot += a[i]*b[i]; na += a[i]*a[i]; nb += b[i]*b[i]; }
        if (na == 0 || nb == 0) return 0;
        return dot / (Math.sqrt(na) * Math.sqrt(nb));
    }
}