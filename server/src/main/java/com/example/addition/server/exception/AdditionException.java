package com.example.addition.server.exception;

public class AdditionException extends Exception {
	String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AdditionException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdditionException{" +
                "message='" + message + '\'' +
                '}';
    }
}
