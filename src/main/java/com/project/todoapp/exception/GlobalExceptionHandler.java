package com.project.todoapp.exception;

import java.rmi.AlreadyBoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  // handling global exception
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
    System.out.println("Vao ExceptionHandler: " + exception.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AlreadyBoundException.class)
  public ResponseEntity<?> customAlreadyTakenException (
      AlreadyBoundException  exception, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(),  exception.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  //	handle custom validation errors
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> customValidationErrorsHandling(
      MethodArgumentNotValidException exception) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation errors",
        exception.getBindingResult().getFieldError().getDefaultMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
