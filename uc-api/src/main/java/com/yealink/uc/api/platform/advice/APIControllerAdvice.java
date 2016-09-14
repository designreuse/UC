package com.yealink.uc.api.platform.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.response.APIResponse;
import com.yealink.uc.platform.rest.error.ErrorResponseBuilder;
import com.yealink.uc.platform.rest.error.FieldError;
import com.yealink.uc.platform.rest.error.ErrorResponse;
import com.yealink.uc.platform.rest.exception.AuthorizationFailedException;
import com.yealink.uc.platform.rest.exception.BadRequestException;
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
public class APIControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public APIResponse error(Throwable e) {
        return APIResponse.buildErrorResponse(ErrorResponseBuilder.createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(BusinessHandleException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public APIResponse businessError(BusinessHandleException e) {
        return APIResponse.buildErrorResponse(ErrorResponseBuilder.createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public APIResponse unauthorized(AuthorizationFailedException e) {
        return APIResponse.buildErrorResponse(ErrorResponseBuilder.createErrorResponse(e, HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse validationError(HttpMessageNotReadableException e) {
        return APIResponse.buildErrorResponse(createValidationResponse(e, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse validationError(TypeMismatchException e) {
        return APIResponse.buildErrorResponse(createValidationResponse(e, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse validationError(BindException e) {
        return APIResponse.buildErrorResponse(createValidationResponse(e.getBindingResult(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse validationError(MethodArgumentNotValidException e) {
        return APIResponse.buildErrorResponse(createValidationResponse(e.getBindingResult(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public APIResponse notFound(ResourceNotFoundException e) {
        return APIResponse.buildErrorResponse(ErrorResponseBuilder.createErrorResponse(e, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public APIResponse methodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return APIResponse.buildErrorResponse(ErrorResponseBuilder.createErrorResponse(e, HttpStatus.METHOD_NOT_ALLOWED));
    }

    private ErrorResponse createValidationResponse(Exception e, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse();
        FieldError error = new FieldError();
        error.setErrorMessage(e.getMessage());
        response.setFieldErrors(Arrays.asList(error));
        response.setStatusCode(httpStatus.value());
        return response;
    }

    private ErrorResponse createValidationResponse(BindingResult bindingResult, HttpStatus httpStatus) {
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
        response.setStatusCode(httpStatus.value());
        response.setExceptionClass(BadRequestException.class.getSimpleName());
        return response;
    }
}
