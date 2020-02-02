package com.example.addition.server.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addition.server.common.Constants;
import com.example.addition.server.dao.AdditionDao;
import com.example.addition.server.entity.AdditionEntity;
import com.example.addition.server.exception.AdditionException;

@Service
public class AdditionServiceImpl implements AdditionService {
	
	@Autowired
    private AdditionDao additionDao;
	
	@Override
	public BigInteger processData(String payloadRequest, HttpServletRequest request) throws NumberFormatException, InterruptedException, AdditionException {
		//validate here
		//find type of request
		boolean isEndOfRequest = isEndOfRequest(payloadRequest);
		String sessionId = generateSessionId(request);
		if(isEndOfRequest) {
			List<AdditionEntity> listOfEntities = additionDao.findAllEntities(sessionId);
			Iterator<AdditionEntity> iterator = listOfEntities.iterator();
			BigInteger sum = additionDao.getSum(sessionId);
			while(iterator.hasNext()) {
				AdditionEntity nextEntity = iterator.next();
				nextEntity.setReadyForResponse(true);
				additionDao.saveEntity(nextEntity);
			} 
			return sum;
		} else {
			AdditionEntity entity = createObject(payloadRequest, sessionId);
			additionDao.saveEntity(entity);
			while(!additionDao.canRespond(entity.getId(), entity.getSessionId())){
				TimeUnit.SECONDS.sleep(1);
			 }
			 BigInteger updatedSum = additionDao.getSum(sessionId);
			 additionDao.deleteEntity(entity);
			 return updatedSum;
		} 
	}
	
	public boolean isEndOfRequest(String payloadRequest) {
		return payloadRequest.equals(Constants.END_STRING);
	}
	
	public AdditionEntity createObject(String payloadRequest, String sessionId) throws AdditionException {
		AdditionEntity entity = new AdditionEntity();
		entity.setNumber(new BigInteger(payloadRequest));
		entity.setSessionId(sessionId);
		entity.setReadyForResponse(false);
		return entity;
	}
	
	public String generateSessionId(HttpServletRequest request) throws AdditionException{
		String ipAddress = request.getRemoteAddr();
		String sessionId = null;
		try {
			sessionId = toHexString(getSHA(ipAddress + Constants.SECRET_KEY));
		} catch (NoSuchAlgorithmException e) {
			throw new AdditionException(e.getMessage());
		}
		return sessionId;
	}
	
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
    public static String toHexString(byte[] hash) 
    { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    } 

}
