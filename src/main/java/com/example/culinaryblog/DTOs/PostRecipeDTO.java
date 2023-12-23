package com.example.culinaryblog.DTOs;

import com.example.culinaryblog.models.Ingredient;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRecipeDTO {

    private String timeToMake;

    private String recipeName;

    private String text;

    private MultipartFile file;

    private String shortDescription;

    private Set<String> ingredients;
}
