package com.example.addition.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addition.server.common.Constants;
import com.example.addition.server.dao.AdditionDao;
import com.example.addition.server.exception.AdditionException;

@Service
public class AdditionServiceImpl implements AdditionService {
	
	@Autowired
    private AdditionDao additionDao;

	@Override
	public Long processData(String payloadRequest) {
		try {
			if(payloadRequest.equals(Constants.END_STRING))
			{
				return additionDao.getSum();
			} else {
				additionDao.saveNumber(new Long(payloadRequest));	
				return new Long(payloadRequest);
			}
		} catch (AdditionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
