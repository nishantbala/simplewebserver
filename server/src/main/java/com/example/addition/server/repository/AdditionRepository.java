package com.example.addition.server.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.addition.server.entity.AdditionEntity;
 
public interface AdditionRepository extends JpaRepository<AdditionEntity, Long> {
 
}