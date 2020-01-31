package com.example.addition.server.service;

import javax.servlet.http.HttpSession;

public interface AdditionService {
	Long processData(String payloadRequest, HttpSession session);	
}
