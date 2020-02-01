package com.example.addition.server.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private AdditionService additionService;
	
	@PostMapping()
	public ResponseEntity<?> processRequest(@RequestBody String payloadRequest, HttpSession session) {
		Long result;
		ResponseEntity<?> entity;
		try {
			result = additionService.processData(payloadRequest,session);
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InterruptedException e) {
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AdditionException e) {
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
