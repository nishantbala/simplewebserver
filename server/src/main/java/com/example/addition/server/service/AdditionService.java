package com.example.addition.server.service;

import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import com.example.addition.server.exception.AdditionException;

public interface AdditionService {
	BigInteger processData(String payloadRequest, HttpSession session) throws NumberFormatException, InterruptedException, AdditionException;	
}
