package com.nonso.ecommercejumiaclone.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductDoesNotExistException extends IllegalArgumentException {
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
