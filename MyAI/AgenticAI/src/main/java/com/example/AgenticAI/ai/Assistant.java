package com.example.AgenticAI.ai;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

    @SystemMessage("""
        You are an agent that can:
        (1) Manage 'Information' records via TOOLS (showAll, add, update, delete).
        (2) Answer general questions using provided CONTEXT.

        Rules:
        - If user wants CRUD, ALWAYS use tools.
        - If it's a knowledge question, use CONTEXT strictly.
        - If context doesn't have the answer, say you don't know.
        - Reply in Hinglish, concise.
        """)
    String chat(@UserMessage String userMessage);
}

