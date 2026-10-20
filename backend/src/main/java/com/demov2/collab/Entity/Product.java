package com.demov2.collab.Entity;

import com.demov2.collab.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {

    @Enumerated(EnumType.STRING)
//    private Category category;
    private  Integer price;
    private String manufacturor;
    private Integer  availableStock;

}
