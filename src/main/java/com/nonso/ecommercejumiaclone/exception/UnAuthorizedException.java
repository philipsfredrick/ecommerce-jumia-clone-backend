package com.nonso.ecommercejumiaclone.exception;

import com.nonso.ecommercejumiaclone.dto.ErrorCode;

import java.io.Serial;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnAuthorizedException extends JumiaCloneException{

    @Serial
    private static final long serialVersionUID = -321597052401234702L;

    public UnAuthorizedException(String message) {
        super(message, UNAUTHORIZED);
    }

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, UNAUTHORIZED, errorCode);
    }
}
