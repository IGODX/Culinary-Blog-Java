package com.example.culinaryblog.services;

import com.example.culinaryblog.DTOs.RegistrationDTO;
import com.example.culinaryblog.models.Role;
import com.example.culinaryblog.models.User;
import com.example.culinaryblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User saveFromDto(RegistrationDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setAccountNonLocked(true);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
//        user.setRole(Role.ADMIN);
       return userRepository.save(user);
    }

    public Optional<User> findFirstByUsername(String username){
        return userRepository.findFirstByUsername(username);
    }



}
