package com.example.addition.server.controller;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.addition.server.exception.AdditionException;
import com.example.addition.server.service.AdditionService;

@RestController
@RequestMapping(path = "/")
public class AdditionController {
	
    private final TaskExecutor taskExecutor;
    
    public AdditionController(TaskExecutor taskExecutor) 
    {
        this.taskExecutor = taskExecutor;
    }
    
	@Autowired
	private AdditionService additionService;
	
	@PostMapping()
	public CompletableFuture<ResponseEntity<?>> processRequest(@RequestBody String payloadRequest, HttpServletRequest request) {
		return CompletableFuture.supplyAsync(() -> 
        {
        	BigInteger result;
    		ResponseEntity<?> entity;
    		try {
    			result = additionService.processRequest(payloadRequest,request);
    			entity = new ResponseEntity<>(result, HttpStatus.OK);
    		} catch (NumberFormatException e) {
    			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    		} catch (InterruptedException e) {
    			Thread.currentThread().interrupt();
    			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    		} catch (AdditionException e) {
    			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    		}
    		return entity;
        }, taskExecutor);
		
	}
}
