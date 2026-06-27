package com.sriram.themevest.controller;

import com.sriram.themevest.dto.CreateThemeRequest;
import com.sriram.themevest.entity.Theme;
import com.sriram.themevest.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    @GetMapping("/{id}")
    public Theme getTheme(@PathVariable Long id) {
        return themeService.getTheme(id);
    }

    @PostMapping
    public Theme createTheme(
            @Valid @RequestBody CreateThemeRequest request) {

        return themeService.createTheme(request);
    }
    @PutMapping("/{id}")
    public Theme updateTheme(
            @PathVariable Long id,
            @Valid @RequestBody CreateThemeRequest request) {

        return themeService.updateTheme(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }
}
