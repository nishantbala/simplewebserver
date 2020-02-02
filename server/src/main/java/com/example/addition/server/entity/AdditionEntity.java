package com.example.addition.server.entity;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_ADDITION")
public class AdditionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private BigInteger number;
	private String sessionId;
	private boolean readyForResponse;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the number
	 */
	public BigInteger getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(BigInteger number) {
		this.number = number;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the readyForResponse
	 */
	public boolean isReadyForResponse() {
		return readyForResponse;
	}
	/**
	 * @param readyForResponse the readyForResponse to set
	 */
	public void setReadyForResponse(boolean readyForResponse) {
		this.readyForResponse = readyForResponse;
	}

	
}