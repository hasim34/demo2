package com.demov2.collab.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import org.hibernate.mapping.ManyToOne;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
// import javax.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter 
public class OrderItem  extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore 
    private Orders order;

    
    @ManyToOne 
    @JoinColumn (name="product_id")
    private Product product;
    
    private Long quantity;

    private Long unitPrice;

}
