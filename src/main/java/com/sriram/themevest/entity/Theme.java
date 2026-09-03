package com.sriram.themevest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @OneToMany(mappedBy = "theme")
    private Set<ThemeStock> themeStocks;

    @Version
    private Long version;
}