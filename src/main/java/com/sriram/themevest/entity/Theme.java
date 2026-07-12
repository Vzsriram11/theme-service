package com.sriram.themevest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "themes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public  enum RiskLevel
    {
        LOW,
        MEDIUM,
        HIGH
    }
    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;
}