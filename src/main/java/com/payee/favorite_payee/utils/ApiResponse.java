package com.payee.favorite_payee.utils;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	  private String status;
	    private T data;
	    private String message;
	    private LocalDateTime timestamp;

	    // Default constructor
	    public ApiResponse() {}

	    // Constructor
	    public ApiResponse(String status, T data, String message) {
	        this.status = status;
	        this.data = data;
	        this.message = message;
	        this.timestamp = LocalDateTime.now();
	    }

	    // Success
	    public static <T> ApiResponse<T> success(T data, String message) {
	        return new ApiResponse<>("success", data, message);
	    }

	    // Error
	    public static <T> ApiResponse<T> error(String message) {
	        return new ApiResponse<>("error", null, message);
	    }

	    // Getters
	    public String getStatus() { return status; }
	    public T getData() { return data; }
	    public String getMessage() { return message; }
	    public LocalDateTime getTimestamp() { return timestamp; }
}