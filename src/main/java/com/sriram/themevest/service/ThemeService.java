package com.sriram.themevest.service;

import com.sriram.themevest.entity.Theme;
import com.sriram.themevest.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }
}