package com.example.AgenticAI.repository;

import com.example.AgenticAI.dto.DTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<DTO,Long> {
}
