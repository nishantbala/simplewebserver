package com.example.addition.server.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.addition.server.entity.AdditionEntity;
 
public interface AdditionRepository extends JpaRepository<AdditionEntity, Long> {
	List<AdditionEntity> findBySessionId(String sessionId);
}