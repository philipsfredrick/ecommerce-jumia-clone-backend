package com.nonso.ecommercejumiaclone.exception;

import com.nonso.ecommercejumiaclone.payload.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class OrderServiceException extends JumiaCloneException {
    public OrderServiceException(String msg) {
        super(msg);
    }

    public OrderServiceException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public OrderServiceException(String msg, HttpStatus status, ErrorCode errorCode) {
        super(msg, status, errorCode);
    }
}
