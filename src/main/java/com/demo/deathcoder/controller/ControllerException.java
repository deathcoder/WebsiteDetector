package com.demo.deathcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by davide on 19/10/16.
 */
@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
public class ControllerException extends Exception {
    private static final long serialVersionUID = 1L;
    private String exceptionCode;
    private String exceptionMessage;
    private boolean error;
    private Integer httpCode;

    public ControllerException() {
        this.error = true;
    }

    public ControllerException( String message ) {
        super( message );
        this.error = true;
    }

    public ControllerException( String message, Throwable cause ) {
        super( message, cause );
        this.error = true;
    }

    public ControllerException( Throwable cause ) {
        super( cause );
        this.error = true;
    }

    public ControllerException( String exceptionCode, String message, Throwable cause ) {
        this( message, cause );
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = message;
    }

    public ControllerException( String exceptionCode, String exceptionMessage ) {
        this( exceptionCode, exceptionMessage, ( Throwable ) null );
    }

    public ControllerException( String exceptionCode, String exceptionMessage, boolean error ) {
        this( exceptionCode, exceptionMessage );
        this.error = error;
    }

    public ControllerException( String exceptionCode, String exceptionMessage, boolean error, Integer httpCode ) {
        this( exceptionCode, exceptionMessage, error );
        this.httpCode = httpCode;
    }

    public ControllerException( String exceptionCode, String exceptionMessage, boolean error, HttpStatus httpCode ) {
        this( exceptionCode, exceptionMessage, error, httpCode != null ? Integer.valueOf( httpCode.value() ) : null );
    }

    public String getExceptionCode() {
        return this.exceptionCode;
    }

    public void setExceptionCode( String exceptionCode ) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }

    public void setExceptionMessage( String exceptionMessage ) {
        this.exceptionMessage = exceptionMessage;
    }

    public boolean isError() {
        return this.error;
    }

    public void setError( boolean error ) {
        this.error = error;
    }

    public Integer getHttpCode() {
        return this.httpCode;
    }

    public void setHttpCode( Integer httpCode ) {
        this.httpCode = httpCode;
    }
}

