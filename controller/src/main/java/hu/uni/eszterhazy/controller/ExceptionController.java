package hu.uni.eszterhazy.controller;


import hu.uni.eszterhazy.exceptions.NotMatchingID;
import hu.uni.eszterhazy.service.exceptions.InvalidRange;
import hu.uni.eszterhazy.service.exceptions.TaskAlreadyExists;
import hu.uni.eszterhazy.service.exceptions.TaskNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String jsonmappingexception(
            HttpMessageNotReadableException ex) {
        Throwable c = ex.getCause().getCause();
        return c.getClass().getSimpleName() + ": "
                + c.getMessage();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String unsupported(HttpMediaTypeNotSupportedException e) {
        return "Use one of the followings: "
                + e.getSupportedMediaTypes();

    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String methodnotallowed(HttpRequestMethodNotSupportedException e){
        return "This method is not allowed "
                +e.getMethod()+", use one of these"
                +e.getSupportedHttpMethods();
    }

    @ExceptionHandler(TaskAlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.IM_USED)
    public String duplicatedtask(TaskAlreadyExists e){
        return "Task already exists with this id: "+e.getMessage();
    }


    @ExceptionHandler(TaskNotFound.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String taskNotFound(TaskNotFound e){
        return "Task not found with this id: "+e.getMessage();
    }


    @ExceptionHandler(InvalidRange.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String invalidrange(InvalidRange e){
        return "Invalid range for list by dates "+e.getMessage();
    }


    @ExceptionHandler(NotMatchingID.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String notMatchingID(NotMatchingID e){
        return "This ID does not match the standard: "+e.getMessage() + ". It should be 2 characters of digits.";
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public String handleNotFoundError(NoHandlerFoundException ex, HttpServletRequest request) {
        return "404";
    }

}