package com.example.addition.server.dao;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.addition.server.common.Constants;
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
	
	private List<AdditionEntity> getAllNumbers() throws AdditionException {
		return additionRepository.findAll();
    }
	
	private void deleteAllNumbers() throws AdditionException {
		additionRepository.deleteAll();
    }
	
	public Long getSum(HttpSession session) throws AdditionException {
		List<AdditionEntity> listOfNumbers = getAllNumbers();
		Iterator<AdditionEntity> iterator = listOfNumbers.iterator();
		Long sum = 0l;
		while(iterator.hasNext()) {
			AdditionEntity nextEntity = iterator.next();
			sum += nextEntity.getNumber();
		}
		deleteAllNumbers();
		session.setAttribute(Constants.SUM, sum);
		return sum;
    }
	
	

}