package com.demov2.collab.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ApiSuccessResponse<T> {

    private LocalDateTime timeStamp;

    private int status;

    private T  data;

    private String successmessage;


}
