package com.example.culinaryblog.controllers;

import com.example.culinaryblog.DTOs.EditRecipeDTO;
import com.example.culinaryblog.DTOs.PostRecipeDTO;
import com.example.culinaryblog.config.SecurityUtil;
import com.example.culinaryblog.models.Ingredient;
import com.example.culinaryblog.models.Recipe;
import com.example.culinaryblog.models.User;
import com.example.culinaryblog.services.FileHandler;
import com.example.culinaryblog.services.IngredientService;
import com.example.culinaryblog.services.RecipeService;
import com.example.culinaryblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private FileHandler fileHandler;

    @GetMapping("/details/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {

        Recipe recipe = recipeService.findById(id).orElse(null);
        if (recipe != null) {
            model.addAttribute("recipe", recipe);
            return "recipe/recipeDetails";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/add")
    public String getAddRecipe(Model model) {
        return "recipe/addRecipe";
    }

    @GetMapping("/edit/{id}")
    public String getEditRecipe(@PathVariable Long id,Model model) {
        var recipe = recipeService.findById(id).orElse(null);
        if(recipe == null)
            return "redirect:/";
        EditRecipeDTO dto = new EditRecipeDTO();

        dto.setText(recipe.getText());

        dto.setRecipeName(recipe.getRecipeName());

        dto.setTimeToMake(recipe.getTimeToMake());

        dto.setShortDescription(recipe.getShortDescription());

        dto.setId(recipe.getId());

        dto.setIngredients(recipe.getIngredients());

        model.addAttribute("dto", dto);

        return "recipe/editRecipe";
    }
    @PostMapping("/edit/{id}")
    public String postEditRecipe(@PathVariable Long id,@ModelAttribute EditRecipeDTO dto,Model model) {
       var recipe = recipeService.findById(id).orElse(null);
       if(recipe == null)
           return "redirect:/";

       recipe.setRecipeName(dto.getRecipeName());

       recipe.setText(dto.getText());

       recipe.setTimeToMake(dto.getTimeToMake());

       recipe.setShortDescription(dto.getShortDescription());

       if(dto.getFile() != null && !dto.getFile().isEmpty()) {
           String imageUrl = fileHandler.storeFile(dto.getFile());
           recipe.setImageUrl(imageUrl);
       }

//
//        ingredientService.saveAll(dto.getNewIngredients(), recipe);

        recipeService.save(recipe);

       return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(@PathVariable Long id,Model model) {
        recipeService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String postAddRecipe(@ModelAttribute PostRecipeDTO dto, Model model) {
        String imageUrl = fileHandler.storeFile(dto.getFile());
        if(imageUrl == null)
            return "recipe/addRecipe";

        Recipe recipe = new Recipe();

        recipe.setRecipeName(dto.getRecipeName());

        recipe.setTimeToMake(dto.getTimeToMake());

        recipe.setText(dto.getText());

        recipe.setShortDescription(dto.getShortDescription());

        recipe.setImageUrl(imageUrl);

        recipe.setPublishDate(new Date());

        User userFromSession = SecurityUtil.getUserFromSession();

        Long userId = userFromSession != null ? userFromSession.getId() : null;

        if(userId == null)
            return "recipe/addRecipe";

         User user = userService.findById(userId).orElse(null);
         if(user == null)
             return "recipe/addRecipe";

         recipe.setUser(user);

        recipeService.save(recipe);

       ingredientService.saveAll(dto.getIngredients(), recipe);

        return "redirect:/";
    }
}
