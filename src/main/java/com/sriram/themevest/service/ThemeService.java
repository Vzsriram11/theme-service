package com.sriram.themevest.service;

import com.sriram.themevest.dto.CreateThemeRequest;
import com.sriram.themevest.entity.Theme;
import com.sriram.themevest.exception.ThemeNotFoundException;
import com.sriram.themevest.repository.ThemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Theme getTheme(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() ->
                        new ThemeNotFoundException(id));
    }

    public Theme createTheme(CreateThemeRequest request) {

        Theme theme = Theme.builder()
                .name(request.getName())
                .description(request.getDescription())
                .riskLevel(Theme.RiskLevel.valueOf(request.getRiskLevel()))
                .build();

        return themeRepository.save(theme);
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }

    public Theme updateTheme(Long id, CreateThemeRequest request)
    {
        Theme existingTheme = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException(id));
        existingTheme.setName(request.getName());
        existingTheme.setDescription(request.getDescription());
        existingTheme.setRiskLevel(Theme.RiskLevel.valueOf(request.getRiskLevel()));
        return  themeRepository.save(existingTheme);
    }
//@CircuitBreaker()
    public  String invokeExternalService ()
    {
        //call external service
        return "success";
    }
}