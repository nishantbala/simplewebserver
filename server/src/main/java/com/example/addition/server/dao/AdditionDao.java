package com.example.addition.server.dao;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.example.addition.server.common.Constants;
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
		cacheManager.getCache(Constants.CACHE_ADDITION).put(entity.getSessionId(), entity.isReadyForResponse());
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
		ValueWrapper cachedItem = cacheManager.getCache(Constants.CACHE_ADDITION).get(sessionID);
		
		if(cachedItem == null) {
			canRespondValue = false;
		}
		else {
			canRespondValue = (boolean)cachedItem.get();
		}			
		return canRespondValue;
	}
	
	public BigInteger getSumFromCache(String sessionId) throws AdditionException {
		ValueWrapper cachedItem = cacheManager.getCache(Constants.CACHE_SUM).get(sessionId);
		BigInteger sum = BigInteger.ZERO;
		if(cachedItem == null) {
			sum = getSum(sessionId);
			cacheManager.getCache(Constants.CACHE_SUM).put(sessionId, sum);
			return sum;
		}
		else {
			return (BigInteger) cachedItem.get();
		}	
    }
	
	public BigInteger getSum(String sessionId) throws AdditionException{
		BigInteger sum = BigInteger.ZERO;
		List<AdditionEntity> listOfEntities = findAllEntities(sessionId);
		Iterator<AdditionEntity> iterator = listOfEntities.iterator();
		while(iterator.hasNext()) {
			AdditionEntity nextEntity = iterator.next();
			sum = sum.add(nextEntity.getNumber());
		}
			return sum;
	}
	
	public void clearCaches() {
		cacheManager.getCache(Constants.CACHE_SUM).clear();
		cacheManager.getCache(Constants.CACHE_ADDITION).clear();
	}
}