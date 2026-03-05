package com.example.DeepakAi.rag;

import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.List;

public class SimpleChunker {

    /**
     * Fixed-size character-based chunking with overlap.
     * Example: chunkSize=300, overlap=50
     */
    public static List<TextSegment> chunk(String text, int chunkSize, int overlap) {
        List<TextSegment> segments = new ArrayList<>();
        if (text == null || text.isBlank()) return segments;

        int start = 0;
        int len = text.length();
        while (start < len) {
            int end = Math.min(start + chunkSize, len);
            String piece = text.substring(start, end);
            segments.add(TextSegment.from(piece));

            if (end == len) break;
            start = Math.max(0, end - overlap);
        }
        return segments;
    }
}