package com.example.culinaryblog.config;

import com.example.culinaryblog.models.User;
import com.example.culinaryblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));
    }
    public void banUser(Long id) {
      User user =  userRepository.findById(id).orElse(null);
      if(user == null)
          return;
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    public void unbanUser(Long id) {
        User user =  userRepository.findById(id).orElse(null);
        if(user == null)
            return;
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }

}
