package com.example.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<String> handleOptimisticLockingFailure(OptimisticLockingFailureException ex) {
		return new ResponseEntity<>("Conflict: Another transaction has updated this entity. Please try again.",
				HttpStatus.CONFLICT);
	}
}
