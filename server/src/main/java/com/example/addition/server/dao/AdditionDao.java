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
import com.example.addition.server.repository.AdditionRepository;

@Component
public class AdditionDao {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
    private AdditionRepository additionRepository;
	
	public void saveEntity(AdditionEntity entity){
		additionRepository.save(entity);
    }
	public void saveReadytoRespond(String sessionId) {
		cacheManager.getCache(Constants.CACHE_ADDITION).put(sessionId, true);
    }	
	public List<AdditionEntity> findAllEntities(String sessionId){
		return additionRepository.findBySessionId(sessionId);
    }
	
	public void deleteAllEntities(List<AdditionEntity> entities) {
		additionRepository.deleteInBatch(entities);
    }
	
	public boolean canRespond(String sessionID) {
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
	
	public BigInteger getSumFromCache(String sessionId) {
		ValueWrapper cachedItem = cacheManager.getCache(Constants.CACHE_SUM).get(sessionId);
		BigInteger sum;
		if(cachedItem == null) {
			sum = getSum(sessionId);
			cacheManager.getCache(Constants.CACHE_SUM).put(sessionId, sum);
			return sum;
		}
		else {
			return (BigInteger) cachedItem.get();
		}	
    }
	
	public BigInteger getSum(String sessionId){
		BigInteger sum = BigInteger.ZERO;
		List<AdditionEntity> listOfEntities = findAllEntities(sessionId);
		Iterator<AdditionEntity> iterator = listOfEntities.iterator();
		while(iterator.hasNext()) {
			AdditionEntity nextEntity = iterator.next();
			sum = sum.add(nextEntity.getNumber());
		}
			return sum;
	}
	
	public void clearCache(String sessionId) {
		clearAdditionCache(sessionId);
		clearSumCache(sessionId);
	}
	
	public void clearAdditionCache(String sessionId) {
		cacheManager.getCache(Constants.CACHE_ADDITION).evict(sessionId);
	}
	
	public void clearSumCache(String sessionId) {
		cacheManager.getCache(Constants.CACHE_SUM).evict(sessionId);
	}
}