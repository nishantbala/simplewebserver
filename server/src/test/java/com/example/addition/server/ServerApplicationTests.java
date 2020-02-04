package com.example.addition.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value="test")
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
	
	@Test
	public void testDecimalRequest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/").content("3.2"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		this.mockMvc.perform(asyncDispatch(mvcResult))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("Invalid input: 3.2"));
	}
	
	@Test
	public void testBigIntegerRequest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/").content("10000000001"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		this.mockMvc.perform(asyncDispatch(mvcResult))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("Max Value Exceeded"));
	}
	
}
