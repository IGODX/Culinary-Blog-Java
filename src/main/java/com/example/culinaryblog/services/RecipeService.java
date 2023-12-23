package com.example.culinaryblog.services;

import com.example.culinaryblog.models.Recipe;
import com.example.culinaryblog.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private FileHandler fileHandler;

    public Optional<Recipe> findById(long id){
        return recipeRepository.findById(id);
    }

    public Recipe save(Recipe recipe){
       return recipeRepository.save(recipe);
    }

    public void delete(Long id){
        var recipe = recipeRepository.findById(id).orElse(null);
        if(recipe == null)
            return;
        fileHandler.deleteFile(recipe.getImageUrl());
       recipe.setUser(null);
        recipeRepository.delete(recipe);
    }
}
