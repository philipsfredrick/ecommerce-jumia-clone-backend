package com.nonso.ecommercejumiaclone.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private boolean isSuccess;
    private T data;

    public ApiResponse(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public String getTimeStamp() {
        return LocalDateTime.now().toString();
    }
    
}
