package com.example.demo.handler;

import com.example.demo.handler.dto.ResponseError;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseError notFoundException(Exception e) {
        ResponseError responseError = getResponseError();
        responseError.setMessage(e.getMessage());
        return responseError;
    }

    @ExceptionHandler(value = {TokenExpiredException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseError expiredException(Exception e) {
        ResponseError responseError = getResponseError();
        responseError.setMessage(e.getMessage());
        return responseError;
    }

    @ExceptionHandler(value = {EmailException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseError emailException(Exception e) {
        ResponseError responseError = getResponseError();
        responseError.setMessage(e.getMessage());
        return responseError;
    }

    @ExceptionHandler(value = {GenericException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError genericException(Exception e) {
        ResponseError responseError = getResponseError();
        responseError.setMessage(e.getMessage());
        return responseError;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseError validationException(Exception e) {
        ResponseError responseError = getResponseError();
        String message = e.getMessage();
        responseError.setMessage(message);
        return responseError;
    }

    @ExceptionHandler(value = {PSQLException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseError psqlException(Exception e) {
        ResponseError responseError = getResponseError();
        responseError.setMessage(e.getMessage());
        return responseError;
    }
    private ResponseError getResponseError() {
        return new ResponseError();
    }
}
