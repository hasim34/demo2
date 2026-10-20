package com.demov2.collab.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.demov2.collab.Entity.Orders;

@Data
@Getter
@Setter
@Builder 
@AllArgsConstructor 
public class ApiSuccessResponse<T> {

    private LocalDateTime timeStamp;

    private int status;

    private T  data;

    private String successmessage;


}
