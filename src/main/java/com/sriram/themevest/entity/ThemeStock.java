
package com.sriram.themevest.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "theme_stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ThemeStock {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "theme_id")
private Theme theme;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "stock_id")
private Stock stock;

@Column(nullable = false)
private BigDecimal allocationPercentage;

    private LocalDateTime addedAt;

}

