package com.example.AgenticAI.controller;


import com.example.AgenticAI.rag.IngestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rag")
public class RagIngestController {

    private final IngestionService ingestionService;

    public RagIngestController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public Map<String, Object> ingest(@RequestBody Map<String, Object> body) {
        String docId = (String) body.getOrDefault("docId", UUID.randomUUID().toString());
        String text = (String) body.getOrDefault("text", "");
        Map<String, Object> meta = (Map<String, Object>) body.getOrDefault("metadata", Map.of());
        int chunks = ingestionService.ingest(docId, text, meta);
        return Map.of("docId", docId, "chunksStored", chunks);
    }
}