package com.example.addition.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.example.addition.server.common.Constants;
import com.example.addition.server.entity.AdditionEntity;
import com.example.addition.server.repository.AdditionRepository;

public class AdditionDaoTest {
		
	@InjectMocks
	private AdditionDao additionDao; 
	
	@Mock
	private AdditionRepository additionRepository;
	
	@Mock
	private CacheManager cacheManager;
	
	private AdditionEntity additionEntity;
	
    @BeforeEach
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        this.additionEntity = new AdditionEntity();
        this.additionEntity.setId(1l);
        this.additionEntity.setNumber(new BigInteger("10000"));
        this.additionEntity.setSessionId("someSessionId");
    }
    
	  @Test
	  public void testSaveAndFind() throws Exception {
		  List<AdditionEntity> allTestEntities = new ArrayList<> ();
		  allTestEntities.add(this.additionEntity);
		  when(additionRepository.findBySessionId(this.additionEntity.getSessionId())).thenReturn(allTestEntities);
		  List<AdditionEntity> allEntities = this.additionDao.findAllEntities(this.additionEntity.getSessionId());
		  assertTrue((allEntities.contains(this.additionEntity)));
	  }
	  
	  @Test
	  public void testCanRespond1() throws Exception {
		  Cache cache = mock(Cache.class);
		  ValueWrapper value = mock(ValueWrapper.class);
		  when(cacheManager.getCache(Constants.CACHE_ADDITION)).thenReturn(cache);
		  when(cache.get(additionEntity.getSessionId())).thenReturn(value);
		  when(value.get()).thenReturn((boolean) true);
		  assertTrue(this.additionDao.canRespond(this.additionEntity.getSessionId()));
	  }
	  
	  @Test
	  public void testSaveReadytoRespond() throws Exception {
		  Cache cache = mock(Cache.class);
		  ValueWrapper value = mock(ValueWrapper.class);
		  when(cacheManager.getCache(Constants.CACHE_ADDITION)).thenReturn(cache);
		  when(cache.get(additionEntity.getSessionId())).thenReturn(value);
		  when(value.get()).thenReturn((boolean) true);
		  this.additionDao.saveReadytoRespond(this.additionEntity.getSessionId());
		  assertTrue(true);
	  }
	  
	  @Test
	  public void testGetSumFromCache() throws Exception {
		  Cache cache = mock(Cache.class);
		  ValueWrapper value = mock(ValueWrapper.class);
		  when(cacheManager.getCache(Constants.CACHE_SUM)).thenReturn(cache);
		  when(cache.get(additionEntity.getSessionId())).thenReturn(value);
		  when(value.get()).thenReturn(new BigInteger("10000"));
		  assertEquals(this.additionDao.getSumFromCache(this.additionEntity.getSessionId()), new BigInteger("10000"));
	  }
	  
	  @Test
	  public void testGetSum() throws Exception{
		  assertTrue(this.additionDao.getSum(this.additionEntity.getSessionId()) instanceof BigInteger);
      }
	
}
