package com.yealink.uc.service.platform.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yealink.uc.platform.rest.error.ErrorResponse;
import com.yealink.uc.platform.rest.error.ErrorResponseBuilder;
import com.yealink.uc.platform.rest.error.FieldError;
import com.yealink.uc.platform.rest.exception.AuthorizationFailedException;
import com.yealink.uc.platform.rest.exception.ResourceNotFoundException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class RESTControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse error(Throwable e) {
        return ErrorResponseBuilder.createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse unauthorized(AuthorizationFailedException e) {
        return ErrorResponseBuilder.createErrorResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(HttpMessageNotReadableException e) {
        return createValidationResponse(e);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(TypeMismatchException e) {
        return createValidationResponse(e);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(BindException e) {
        return createValidationResponse(e.getBindingResult());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(MethodArgumentNotValidException e) {
        return createValidationResponse(e.getBindingResult());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse notFound(ResourceNotFoundException e) {
        return ErrorResponseBuilder.createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorResponse methodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ErrorResponseBuilder.createErrorResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ErrorResponse createValidationResponse(Exception e) {
        ErrorResponse response = new ErrorResponse();
        FieldError error = new FieldError();
        error.setErrorMessage(e.getMessage());
        response.setFieldErrors(Arrays.asList(error));
        return response;
    }

    private ErrorResponse createValidationResponse(BindingResult bindingResult) {
//        Locale locale = LocaleContextHolder.getLocale(); todo
        ErrorResponse response = new ErrorResponse();
        List<FieldError> errorItemList = new ArrayList<>();
        for (org.springframework.validation.FieldError fieldError : bindingResult.getFieldErrors()) {
            FieldError error = new FieldError();
            error.setField(fieldError.getField());
            error.setErrorMessage(fieldError.getDefaultMessage());
            errorItemList.add(error);
        }
        response.setFieldErrors(errorItemList);
        return response;
    }
}
