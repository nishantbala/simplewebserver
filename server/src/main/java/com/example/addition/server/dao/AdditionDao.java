package com.example.addition.server.dao;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.addition.server.entity.AdditionEntity;
import com.example.addition.server.exception.AdditionException;
import com.example.addition.server.repository.AdditionRepository;

@Component
public class AdditionDao {
	
	@Autowired
    private AdditionRepository additionRepository;
	
	public void saveEntity(AdditionEntity entity) throws AdditionException {
		additionRepository.save(entity);
    }
	
	public List<AdditionEntity> findAllEntities() throws AdditionException {
		return additionRepository.findAll();
    }
	
	public void deleteEntity(AdditionEntity entity) throws AdditionException {
		additionRepository.deleteById(entity.getId());
    }
	
	public BigInteger getUpdatedSum(AdditionEntity entity) throws AdditionException {
		Optional<AdditionEntity> optionalEntity = additionRepository.findById(entity.getId());
		AdditionEntity updatedEntity;
		if(optionalEntity.isPresent()) {
			updatedEntity = optionalEntity.get();
			return updatedEntity.getSum();
		}
		return null;
	}
	
	public BigInteger getSum() throws AdditionException {
		List<AdditionEntity> listOfEntities = findAllEntities();
		Iterator<AdditionEntity> iterator = listOfEntities.iterator();
		BigInteger sum = BigInteger.ZERO;
		while(iterator.hasNext()) {
			AdditionEntity nextEntity = iterator.next();
			sum = sum.add(nextEntity.getNumber());
		}
		return sum;
    }
	
	

}