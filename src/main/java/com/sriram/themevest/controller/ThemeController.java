package com.sriram.themevest.controller;

import com.sriram.themevest.entity.Theme;
import com.sriram.themevest.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping
    public List<Theme> getThemes() {
        return themeService.getAllThemes();
    }

    @PostMapping
    public Theme createTheme(@RequestBody Theme theme) {
        return themeService.createTheme(theme);
    }
}
