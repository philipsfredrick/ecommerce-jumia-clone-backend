package com.nonso.ecommercejumiaclone.exception;

import com.nonso.ecommercejumiaclone.payload.ErrorCode;

import org.springframework.http.HttpStatus;



public class ProductServiceException extends JumiaCloneException {
    public ProductServiceException(String msg) {
        super(msg);
    }

    public ProductServiceException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public ProductServiceException(String msg, HttpStatus status, ErrorCode errorCode) {
        super(msg, status, errorCode);
    }
}
