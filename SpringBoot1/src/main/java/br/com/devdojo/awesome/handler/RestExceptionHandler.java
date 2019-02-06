package br.com.devdojo.awesome.handler;

import br.com.devdojo.awesome.error.ResourceNotFoundDetails;
import br.com.devdojo.awesome.error.ResourceNotFoundException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException){
        
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
        
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();
       return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }  
}
