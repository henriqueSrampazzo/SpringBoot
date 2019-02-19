package br.com.devdojo.awesome.handler;

import br.com.devdojo.awesome.error.ErrorDetails;
import br.com.devdojo.awesome.error.ResourceNotFoundDetails;
import br.com.devdojo.awesome.error.ResourceNotFoundException;
import br.com.devdojo.awesome.error.ValidationErrorDetails;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {

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

    @Override

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));

        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails rnfDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field Validation Error")
                .detail("Field Validation Error")
                .developerMessage(manvException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();
        return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
    }

//    @Override
//
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//        ErrorDetails errorDetails = ErrorDetails.Builder
//                .newBuilder()
//                .timestamp(new Date().getTime())
//                .status(HttpStatus.NOT_FOUND.value())
//                .title("Resource not found")
//                .detail(ex.getMessage())
//                .developerMessage(ex.getClass().getName())
//                .build();// <editor-fold defaultstate="collapsed" desc="Compiled Code">
//        /* 0: aload_0
//         * 1: aload_1
//         * 2: aconst_null
//         * 3: aload_2
//         * 4: aload_3
//         * 5: aload         4
//         * 7: invokevirtual org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.handleExceptionInternal:(Ljava/lang/Exception;Ljava/lang/Object;Lorg/springframework/http/HttpHeaders;Lorg/springframework/http/HttpStatus;Lorg/springframework/web/context/request/WebRequest;)Lorg/springframework/http/ResponseEntity;
//         * 10: areturn
//         *  */
//        // </editor-fold>
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//
//    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: getstatic     org/springframework/http/HttpStatus.INTERNAL_SERVER_ERROR:Lorg/springframework/http/HttpStatus;
         * 3: aload         4
         * 5: invokevirtual org/springframework/http/HttpStatus.equals:(Ljava/lang/Object;)Z
         * 8: ifeq          22
         * 11: aload         5
         * 13: ldc           javax.servlet.error.exception
         * 15: aload_1
         * 16: iconst_0
         * 17: invokeinterface org/springframework/web/context/request/WebRequest.setAttribute:(Ljava/lang/String;Ljava/lang/Object;I)V
         * 22: new           org/springframework/http/ResponseEntity
         * 25: dup
         * 26: aload_2
         * 27: aload_3
         * 28: aload         4
         * 30: invokespecial org/springframework/http/ResponseEntity."<init>":(Ljava/lang/Object;Lorg/springframework/util/MultiValueMap;Lorg/springframework/http/HttpStatus;)V
         * 33: areturn
         *  */
        // </editor-fold>
        ErrorDetails errorDetails = ErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Internal Exception")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(errorDetails, headers,status);
    }

}
