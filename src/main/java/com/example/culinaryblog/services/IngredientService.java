package com.example.culinaryblog.services;

import com.example.culinaryblog.models.Ingredient;
import com.example.culinaryblog.models.Recipe;
import com.example.culinaryblog.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientService {
@Autowired
    private IngredientRepository ingredientRepository;

public Set<Ingredient> saveAll(Set<String> ingredients, Recipe recipe) {
    Set<Ingredient> ingredientSet = new HashSet<>();
    ingredients.forEach(ingredient -> {
        if (!ingredient.isBlank())
            ingredientSet.add(ingredientRepository.save(new Ingredient(0, ingredient, recipe)));
    });
    return ingredientSet;
}


public void deleteAll(Set<Ingredient> ingredients){
    for (var ingredient: ingredients
         ) {
        ingredient.setRecipe(null);
        ingredientRepository.delete(ingredient);
    }

}
}
