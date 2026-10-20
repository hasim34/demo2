package com.demov2.collab.Dto;


import lombok.Getter;

@Getter
public class CreateCustomerRequest {
    private String name;

    private String emailId;
    private Long phoneNumber;

}
