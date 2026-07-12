package com.sriram.themevest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
    @Id
    private String ticker;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String exchange;
    @Column(nullable = false)
    private String sector;


}
