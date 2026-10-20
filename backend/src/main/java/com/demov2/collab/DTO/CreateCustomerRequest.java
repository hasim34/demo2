package com.demov2.collab.DTO;


import com.demov2.collab.Entity.Enums.CustomerStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class CreateCustomerRequest {
    @NotNull
    private String name;
    @NotNull

    private long phoneNumber;
    @NotNull(message = "Email is mandatory")
    @Email(message = "enter the valid email")
    private String emailId;
    @NotNull
    private String address;
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;


}
