package com.example.AgenticAI.controller;


import com.example.AgenticAI.ai.Assistant;
import com.example.AgenticAI.rag.RagService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final Assistant assistant;
    private final RagService rag;

    public AgentController(Assistant assistant, RagService rag) {
        this.assistant = assistant;
        this.rag = rag;
    }

    @PostMapping("/ask")
    public Map<String, Object> ask(@RequestBody Map<String, String> body) {
        String user = body.getOrDefault("prompt", "");
        // RAG context build karke final prompt me prepend:
        String context = rag.buildContext(user, 5);

        String finalPrompt = """
                CONTEXT (snippets):
                %s

                USER MESSAGE:
                %s
                """.formatted(context, user);

        String reply = assistant.chat(finalPrompt);
        return Map.of("reply", reply);
    }
}