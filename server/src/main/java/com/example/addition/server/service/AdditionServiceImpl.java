package com.example.addition.server.service;

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
	public Long processData(String payloadRequest, HttpSession session) throws NumberFormatException, InterruptedException, AdditionException {
		if(payloadRequest.equals(Constants.END_STRING))
		{
			session.setAttribute(Constants.IS_END, Constants.IS_END);
			return additionDao.getSum(session);
		} else {
			session.setAttribute(Constants.IS_END, null);
			additionDao.saveNumber(new Long(payloadRequest));	
			while(null == session.getAttribute(Constants.IS_END)) {
				Thread.sleep(1000);
			}
			return new Long((long) session.getAttribute(Constants.SUM));
		}
	}

}
