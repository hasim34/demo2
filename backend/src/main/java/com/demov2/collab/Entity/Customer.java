package com.demov2.collab.Entity;

import com.demov2.collab.Entity.Enums.ProductCategory;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity{

    private long phoneNumber;
    private ProductCategory emailId;
    private String Address;

}
