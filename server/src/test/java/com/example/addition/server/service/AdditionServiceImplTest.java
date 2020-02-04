package com.example.addition.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.addition.server.dao.AdditionDao;

public class AdditionServiceImplTest {
	
    @Mock
    private AdditionDao additionDao;
    
    private AdditionServiceImpl additionServiceImpl = new AdditionServiceImpl();
    
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testIsEndOfRequest() throws Exception {
    	assertTrue(this.additionServiceImpl.isEndOfRequest("end"));
    }
    
    @Test
    public void testValidateRequest() throws Exception {
    	this.additionServiceImpl.validateRequest("end","somesessionid");
    	assertTrue(true);
    }
    
    @Test
    public void testValidateRequest1() throws Exception {
    	String actual = null;
    	try {
    		this.additionServiceImpl.validateRequest("3.2","somesessionid");
    	} catch(Exception e) {
    		actual = e.getMessage();
    	}
    	assertEquals("Invalid input: 3.2", actual);
    }

}
