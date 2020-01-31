package com.example.addition.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addition.server.dao.AdditionDao;
import com.example.addition.server.entity.AdditionEntity;
import com.example.addition.server.exception.AdditionException;

@Service
public class AdditionServiceImpl implements AdditionService {
	
	@Autowired
    private AdditionDao additionDao;

	@Override
	public void processData(String payloadRequest) {
		try {
			additionDao.saveNumber(new Long(payloadRequest));
		} catch (AdditionException e) {
			e.printStackTrace();
		}
	}

}
