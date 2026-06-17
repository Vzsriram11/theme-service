package com.sriram.themevest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThemeController {

    @GetMapping("/themes")
    public List<String> getThemes() {

        return List.of(
                "Artificial Intelligence",
                "Cybersecurity",
                "Cloud Computing"
        );
    }
}
