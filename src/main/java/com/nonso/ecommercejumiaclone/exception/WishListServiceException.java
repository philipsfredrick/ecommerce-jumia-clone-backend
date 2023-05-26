package com.nonso.ecommercejumiaclone.exception;

import com.nonso.ecommercejumiaclone.payload.ErrorCode;
import org.springframework.http.HttpStatus;

public class WishListServiceException extends JumiaCloneException {
    public WishListServiceException(String msg) {
        super(msg);
    }

    public WishListServiceException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public WishListServiceException(String msg, HttpStatus status, ErrorCode errorCode) {
        super(msg, status, errorCode);
    }
}
