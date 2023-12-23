package com.example.culinaryblog.controllers;

import com.example.culinaryblog.config.CustomUserDetailsService;
import com.example.culinaryblog.config.SecurityUtil;
import com.example.culinaryblog.models.User;
import com.example.culinaryblog.repositories.UserRepository;
import com.example.culinaryblog.services.RecipeService;
import com.example.culinaryblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/show-users")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getShowUsers(Model model){

        model.addAttribute("users", userRepository.findAll());

        return "admin/showUsers";
    }

    @PostMapping("/delete-user/{userId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public String deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
        return "redirect:/admin/show-users";
    }

    @PostMapping("/delete-recipe/{recipeId}")
    @PreAuthorize("hasAuthority('admin:delete')")
        public String deleteUserRecipe(@PathVariable Long recipeId){
        recipeService.delete(recipeId);
        return "redirect:/admin/show-users";
    }

    @PostMapping("/ban-user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN:BAN')")
    public String banUser(@PathVariable Long userId){
        customUserDetailsService.banUser(userId);
        return "redirect:/admin/show-users";
    }
    @PostMapping("/unban-user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN:BAN')")
    public String unbanUser(@PathVariable Long userId){
        customUserDetailsService.unbanUser(userId);
        return "redirect:/admin/show-users";
    }
}
