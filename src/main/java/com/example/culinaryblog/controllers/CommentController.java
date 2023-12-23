package com.example.culinaryblog.controllers;

import com.example.culinaryblog.config.SecurityUtil;
import com.example.culinaryblog.models.Comment;
import com.example.culinaryblog.models.Recipe;
import com.example.culinaryblog.models.User;
import com.example.culinaryblog.services.CommentService;
import com.example.culinaryblog.services.RecipeService;
import com.example.culinaryblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comment")
    public String postComment(@ModelAttribute Comment comment, @RequestParam Long recipeId){
        User userFromSession = SecurityUtil.getUserFromSession();
        Long userId = userFromSession != null ? userFromSession.getId() : null;
        if(userId == null)
            return "redirect:/recipe/details/" + recipeId;
        User user = userService.findById(userId).orElse(null);
        if(user == null)
            return "redirect:/recipe/details/" + recipeId;
        comment.setUser(user);
        var recipe = recipeService.findById(recipeId).orElse(null);
        if(recipe == null)
            return "redirect:/recipe/details/" + recipeId;
        comment.setRecipe(recipe);
        comment.setPublishDate(new Date());
        commentService.save(comment);
        return "redirect:/recipe/details/" + recipeId;
    }
}
