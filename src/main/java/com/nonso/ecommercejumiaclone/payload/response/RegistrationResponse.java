package com.nonso.ecommercejumiaclone.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 721601342978661569L;

    private String message;
}
