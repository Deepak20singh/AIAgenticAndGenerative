package com.example.AgenticAI.ai;


import com.example.AgenticAI.dto.DTO;
import com.example.AgenticAI.service.Service;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrudTools {

    private final Service service;

    public CrudTools(Service service) {
        this.service = service;
    }

    @Tool("Saare records dikhata hai.")
    public List<DTO> showAll() {
        return service.showAll();
    }

    @Tool("Naya record add karta hai. name, blog, sign pass karo.")
    public String add(String name, String blog, String sign) {
        DTO dto = new DTO();
        dto.setName(name);
        dto.setBlog(blog);
        dto.setSign(sign);
        service.add(dto);
        return "Added id: " + dto.getId();
    }

    @Tool("Existing record update karta hai by id; null fields ignore hoti hain.")
    public String update(Long id, String name, String blog, String sign) {
        DTO dto = new DTO();
        dto.setName(name);
        dto.setBlog(blog);
        dto.setSign(sign);
        service.update(id, dto);
        return "Updated id: " + id;
    }

    @Tool("Record delete karta hai by id.")
    public String delete(Long id) {
        service.delete(id);
        return "Deleted id: " + id;
    }
}