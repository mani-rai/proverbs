/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.exceptions.handlers;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for all controllers.
 *
 * @author mani
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Handles {@link EntityNotFoundException} and {@link HttpRequestMethodNotSupportedException}.
     *
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, HttpRequestMethodNotSupportedException.class})
    public String handleEntityNotFoundException() {

        return "not_found";
    }

    /**
     * Handles all other uncaught/unhandled exceptions.
     *
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleAllException() {

        return "server_error";
    }
}
