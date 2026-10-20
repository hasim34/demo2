package com.demov2.collab.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(updatable = false)
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

        
    @PrePersist
    protected  void onCreate(){
        createAt=LocalDateTime.now();
        updateAt=LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate(){
        updateAt=LocalDateTime.now();
    }

}
