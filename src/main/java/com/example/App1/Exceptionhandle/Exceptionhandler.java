package com.example.App1.Exceptionhandle;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MethodNotSupportedException;

 @RestControllerAdvice
public class Exceptionhandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex)
	{
		Map<String, String> errorMap = new HashMap<>();
	     ex.getBindingResult().getFieldErrors().forEach(error->{
		 errorMap.put(error.getField(), error.getDefaultMessage());
	 });		
		return errorMap;
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex)
	{
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodNotSupportedException.class)
	public ResponseEntity<String> MethodNotSupportedException(MethodNotSupportedException ex)
	{
		return  new ResponseEntity<String>("what you looking for does not exist",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<String> handleAuthenticationFailedException(AuthenticationFailedException ex)
	{
		return new ResponseEntity<String>("User or PassWord is incorrect",HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex)
	{
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
