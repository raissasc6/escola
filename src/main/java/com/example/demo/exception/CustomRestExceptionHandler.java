package com.example.demo.exception;

import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;


/** Types that carry this annotation are treated as controller advice where @ExceptionHandler methods
 *  assume @ResponseBody semantics by default.
 @RestControllerAdvice is processed if an appropriate HandlerMapping-HandlerAdapter pair is configured such as the
 RequestMappingHandlerMapping-RequestMappingHandlerAdapter pair which are the default in the MVC Java config
 and the MVC namespace.**/
@RestControllerAdvice
public class CustomRestExceptionHandler {
    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ResourceIllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgument(Exception ex){
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


}
