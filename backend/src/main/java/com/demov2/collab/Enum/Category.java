package com.example.orderManagement.Enum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Category {

    @Enumerated(EnumType.STRING)
    private Category category;
    private  int price;

}
