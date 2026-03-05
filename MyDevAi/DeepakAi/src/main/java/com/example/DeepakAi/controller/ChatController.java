package com.example.DeepakAi.controller;


import com.example.DeepakAi.rag.IngestService;
import com.example.DeepakAi.rag.PersonaRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final IngestService ingestService;
    private final PersonaRagService ragService;

    @GetMapping("/init")
    public String init() throws Exception {
        ingestService.ingestPersona();
        return "Deepak-AI personality Loaded!";
    }


    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> req) {
        return Map.of("answer", ragService.answer(req.get("msg")));
    }

}