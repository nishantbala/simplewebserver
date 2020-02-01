package com.example.addition.server.service;

import javax.servlet.http.HttpSession;

import com.example.addition.server.exception.AdditionException;

public interface AdditionService {
	Long processData(String payloadRequest, HttpSession session) throws NumberFormatException, InterruptedException, AdditionException;	
}
