package com.example.culinaryblog.DTOs;

import com.example.culinaryblog.models.Ingredient;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class EditRecipeDTO {
    private long id;


    private MultipartFile file;

    private String timeToMake;

    private String recipeName;

    private String text;

    private String shortDescription;

    private Set<Ingredient> ingredients;

    private Set<String> newIngredients;
}
