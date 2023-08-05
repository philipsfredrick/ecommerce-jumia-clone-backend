package com.nonso.ecommercejumiaclone.exception;

import com.nonso.ecommercejumiaclone.dto.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class JumiaCloneException extends RuntimeException {

    protected HttpStatus httpStatus;

    private ErrorCode errorCode;

    private String infoLink;

    private String metadata;

    public JumiaCloneException(final String message) {
        super(message);
    }

    public JumiaCloneException(final String message, final HttpStatus httpStatus) {
        this(message);
        this.httpStatus = httpStatus;
    }

    public JumiaCloneException(final String message, final HttpStatus httpStatus, final ErrorCode errorCode) {
        this(message, httpStatus);
        this.errorCode = errorCode;
    }

    public JumiaCloneException(final String message, final HttpStatus httpStatus, final String metadata) {
        this(message, httpStatus);
        this.metadata = metadata;
    }


    public JumiaCloneException(final String message, final HttpStatus httpStatus, final ErrorCode errorCode, final String metadata) {
        this(message, httpStatus, errorCode);
        this.metadata = metadata;
    }
}
