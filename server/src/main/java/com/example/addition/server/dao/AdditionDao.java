package com.example.addition.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.addition.server.entity.AdditionEntity;
import com.example.addition.server.exception.AdditionException;
import com.example.addition.server.repository.AdditionRepository;

@Component
public class AdditionDao {
	
	@Autowired
    private AdditionRepository additionRepository;
	
	public void saveNumber(Long number) throws AdditionException {
		AdditionEntity entity = new AdditionEntity();
		entity.setNumber(number);
		additionRepository.save(entity);
    }

}