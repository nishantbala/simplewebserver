package com.example.addition.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testEndRequest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/").content("end"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		this.mockMvc.perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(content().string("0"));
	}
	

    public void testAdditionController() throws Exception 
    {
    	 List<String> listOfRequests = Arrays.asList("1", "2", "3", "end");
    	 List<String> listOfResponses = Arrays.asList("6", "6", "6", "6");
    	 List<MvcResult> mvcResultsList = new ArrayList<>();
    	 Iterator<String> reqIterator = listOfRequests.iterator();
    	 while(reqIterator.hasNext()) {
    		 String request = reqIterator.next();
    		 MvcResult mvcResult = mockMvc.perform(post("/").content(request))
                     .andExpect(request().asyncStarted())
                     .andDo(MockMvcResultHandlers.log())
                     .andReturn();
    		 mvcResultsList.add(mvcResult);
    	 }
         
    	 for(int i=0; i < listOfRequests.size(); i++) {
    		 MvcResult mvcResult  = mvcResultsList.get(i);
    		 mockMvc.perform(asyncDispatch(mvcResult))
             .andExpect(status().isOk())
             .andExpect(content().string(listOfResponses.get(i)));
    	 }
          
    }
}
