package com.demov2.collab.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Setter
@Getter
public class Orders extends BaseEntity {

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>(); 
    
    @ManyToOne 
    @JoinColumn (name = "customer_id")
    private Customer Customer;
    
}
