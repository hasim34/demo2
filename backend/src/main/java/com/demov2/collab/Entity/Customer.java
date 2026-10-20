package com.demov2.collab.Entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer  extends BaseEntity{

    private long phoneNumber;
    private String emailId;
    private String Address;




}
