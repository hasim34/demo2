package com.demov2.collab.DTO;
import  com.demov2.collab.DTO.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data 
public class OrderDto {
    private Long customerId;
    private List<OrderItemDto> items = new ArrayList<>();
}
