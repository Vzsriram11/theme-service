package com.sriram.themevest.controller;

import com.sriram.themevest.dto.CreateThemeRequest;
import com.sriram.themevest.entity.Theme;
import com.sriram.themevest.model.Post;
import com.sriram.themevest.service.PostServiceClient;
import com.sriram.themevest.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor


@Tag(
        name = "Theme API",
        description = "APIs for managing investment themes"
)
public class ThemeController {

    private final ThemeService themeService;
    private final PostServiceClient postService;

    @GetMapping
    @Operation(summary = "Retrieve all investment themes")
    public List<Theme> getAllThemes() {
      /*  for (Post post : postService.getAllPosts()) {
            System.out.println("post:" + post);
        }*/

        return themeService.getAllThemes();
    }

    @GetMapping("/{id}")
    @Operation(summary =  "Get a specific Theme based on ThemeId")
    public Theme getTheme(@PathVariable Long id) {
        return themeService.getTheme(id);
    }

    @PostMapping
    @Operation(summary =  "create a specific Theme")
    public Theme createTheme(
            @Valid @RequestBody CreateThemeRequest request) {

        return themeService.createTheme(request);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing investment theme")
    public Theme updateTheme(
            @PathVariable Long id,
            @Valid @RequestBody CreateThemeRequest request) {

        return themeService.updateTheme(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a theme")
    public void deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }
}
