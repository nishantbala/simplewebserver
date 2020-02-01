package com.example.addition.server.service;

import java.math.BigInteger;

import javax.servlet.http.HttpSession;

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
	public BigInteger processData(String payloadRequest, HttpSession session) throws NumberFormatException, InterruptedException, AdditionException {
		if(validatePayloadRequest(payloadRequest))
		{
			session.setAttribute(Constants.IS_END, true);
			return additionDao.getSum(session);
		} else {
			session.setAttribute(Constants.IS_END, null);
			additionDao.saveNumber(new BigInteger(payloadRequest));	
			while(null == session.getAttribute(Constants.IS_END)) {
				Thread.sleep(1000);
			}
			return (BigInteger) session.getAttribute(Constants.SUM);
		}
	}
	
	public boolean validatePayloadRequest(String payloadRequest) {
		boolean isEnd = payloadRequest.equals(Constants.END_STRING);
		return isEnd;
	}

}
