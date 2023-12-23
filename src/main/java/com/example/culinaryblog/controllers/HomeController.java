package com.example.culinaryblog.controllers;

import com.example.culinaryblog.config.SecurityUtil;
import com.example.culinaryblog.models.Recipe;
import com.example.culinaryblog.models.User;
import com.example.culinaryblog.repositories.RecipeRepository;
import com.example.culinaryblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("recipes",  recipeRepository.findAll());
        return "home";
    }
}
