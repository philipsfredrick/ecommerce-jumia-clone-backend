package com.nonso.ecommercejumiaclone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
