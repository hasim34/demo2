package com.demov2.collab.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  
@NoArgsConstructor 
@AllArgsConstructor 
public class ApiErrorResponse {
    private LocalDateTime timeStamp;

    private int status;

    private String error;

    private String errorMessage;
}
