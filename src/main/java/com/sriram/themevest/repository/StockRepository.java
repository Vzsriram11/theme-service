package com.sriram.themevest.repository;

import com.sriram.themevest.entity.Stock;
import com.sriram.themevest.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
