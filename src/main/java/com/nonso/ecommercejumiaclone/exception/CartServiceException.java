package com.nonso.ecommercejumiaclone.exception;

import com.nonso.ecommercejumiaclone.payload.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
public class CartServiceException extends JumiaCloneException{

    public CartServiceException(String msg) {
        super(msg);
    }

    public CartServiceException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public CartServiceException(String msg, HttpStatus status, ErrorCode errorCode) {
        super(msg, status, errorCode);
    }
}
