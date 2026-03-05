package com.example.AgenticAI.controller;

import com.example.AgenticAI.dto.DTO;
import com.example.AgenticAI.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agentic-ai")
public class Controller {
    @Autowired
    private Service service;
    @GetMapping("/all")
    public ResponseEntity<List<DTO>> showAll() {
        return ResponseEntity.ok(service.showAll());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAll(@PathVariable Long id) {
        service.delete(id);
       return ResponseEntity.ok("Deleted record with id: " + id);
    }
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody DTO dto) {
        service.add(dto);
        return ResponseEntity.ok("Added record with id: " + dto.getId());

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody DTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Updated record with id: " + id);}

}
