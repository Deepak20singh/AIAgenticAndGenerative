package com.example.AgenticAI.service;


import com.example.AgenticAI.dto.DTO;
import com.example.AgenticAI.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repository repository;

    public void add(DTO dto) {
        repository.save(dto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Null-ignore update
    public void update(Long id, DTO dto) {
        DTO existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getBlog() != null) existing.setBlog(dto.getBlog());
        if (dto.getSign() != null) existing.setSign(dto.getSign());
        repository.save(existing);
    }

    public java.util.List<DTO> showAll() {
        return repository.findAll();
    }
}