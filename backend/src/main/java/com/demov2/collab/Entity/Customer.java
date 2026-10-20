package com.demov2.collab.Entity;

import com.demov2.collab.Entity.Enums.CustomerStatus;
import com.demov2.collab.Entity.Enums.ProductCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity{



    private String name;
    private long phoneNumber;
    private String emailId;
    private String address;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;
}
