package com.example.addition.server.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.addition.server.dao.AdditionDao;

public class AdditionServiceImplTest {
	
    @Mock
    private AdditionDao additionDao;
    
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }
}
