package com.example.culinaryblog.controllers;

import com.example.culinaryblog.DTOs.RegistrationDTO;
import com.example.culinaryblog.models.Role;
import com.example.culinaryblog.models.User;
import com.example.culinaryblog.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthorizationController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) Boolean error, Model model){
     if(error != null && error)
        model.addAttribute("loginError", "Wrong login or password!");

     return "authorization/login";
    }


    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("dto", new RegistrationDTO());
        return "authorization/register";
    }

    @PostMapping("/register/save")
    public String postRegister(@Valid @ModelAttribute("dto") RegistrationDTO dto, HttpServletRequest request, BindingResult result, Model model){
        if(validateRegisterRequest(result, dto))
            return "authorization/register";

            User user = userService.saveFromDto(dto);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return "redirect:/";
    }


    private boolean validateRegisterRequest(BindingResult result,RegistrationDTO dto)
    {
        boolean error = false;
        if(dto.getPassword() == null || dto.getPassword().isBlank()) {
            result.rejectValue("password", "Passwords field is required!");
            error = true;
        }

        if(dto.getRepeatPassword() == null || dto.getRepeatPassword().isBlank()) {
            result.rejectValue("password", "Passwords field is required!");
            error = true;
        }
        if(dto.getEmail() == null || dto.getEmail().isBlank()) {
            result.rejectValue("email", "Email field is required!");
            error = true;
        }
        if(dto.getUsername() == null || dto.getUsername().isBlank()) {
            result.rejectValue("username", "Username field is required!");
            error = true;
        }
        if(!dto.getPassword().equals(dto.getRepeatPassword())) {
            result.rejectValue("password", "Passwords must match!");
            error = true;
        }
        boolean exist = userService.isEmailExists(dto.getEmail());

        if(exist) {
            result.rejectValue("email", "There is already a user with this email!");
            error = true;
        }
        exist = userService.isUsernameExists(dto.getUsername());

        if(exist) {
            result.rejectValue("username", "There is already a user with this username!");
            error = true;
        }
        return error;
    }
}
