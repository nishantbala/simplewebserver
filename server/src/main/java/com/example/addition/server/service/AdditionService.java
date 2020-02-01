package com.example.addition.server.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import com.example.addition.server.exception.AdditionException;

public interface AdditionService {
	BigInteger processData(String payloadRequest, HttpServletRequest request) throws NumberFormatException, InterruptedException, AdditionException;	
}
