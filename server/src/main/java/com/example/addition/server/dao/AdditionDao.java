package com.example.addition.server.dao;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.example.addition.server.entity.AdditionEntity;
import com.example.addition.server.exception.AdditionException;
import com.example.addition.server.repository.AdditionRepository;

@Component
public class AdditionDao {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
    private AdditionRepository additionRepository;
	
	public void saveEntity(AdditionEntity entity) throws AdditionException {
		cacheManager.getCache("addition").put(entity.getSessionId(), entity.isReadyForResponse());
		additionRepository.save(entity);
    }
	
	public List<AdditionEntity> findAllEntities(String sessionId) throws AdditionException {
		return additionRepository.findBySessionId(sessionId);
    }
	
	public void deleteEntity(AdditionEntity entity) throws AdditionException {
		additionRepository.deleteById(entity.getId());
    }
	
	public boolean canRespond(long entityID, String sessionID) {
		boolean canRespondValue = false;
		
		//First get cached item
		ValueWrapper cachedItem = cacheManager.getCache("addition").get(sessionID);
		
		if(cachedItem == null) {
			canRespondValue = false;
		}
		else {
			canRespondValue = (boolean)cachedItem.get();
		}			
		return canRespondValue;
	}
	
	public BigInteger getSum(String sessionId) throws AdditionException {
		ValueWrapper cachedItem = cacheManager.getCache("sum").get(sessionId);
		BigInteger sum = BigInteger.ZERO;
		if(cachedItem == null) {
			List<AdditionEntity> listOfEntities = findAllEntities(sessionId);
			Iterator<AdditionEntity> iterator = listOfEntities.iterator();
			while(iterator.hasNext()) {
				AdditionEntity nextEntity = iterator.next();
				sum = sum.add(nextEntity.getNumber());
			}
			cacheManager.getCache("sum").put(sessionId, sum);
			return sum;
		}
		else {
			return (BigInteger) cachedItem.get();
		}	
    }
	
	

}