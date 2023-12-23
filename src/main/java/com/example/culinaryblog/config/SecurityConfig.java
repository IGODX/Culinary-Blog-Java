package com.example.culinaryblog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.culinaryblog.models.Permission.*;
import static com.example.culinaryblog.models.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/",
                                "/recipe/details/{id}",
                                "/register",
                                "/register/save",
                                "/css/**",
                                "/files/**",
                                "/error**",
                                "/add-comment",
                                "/recipeImages/**").permitAll()
                        .requestMatchers("/recipe/add").authenticated()
                        .requestMatchers("/recipe/edit/**").authenticated()
                        .requestMatchers("/admin/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers("/admin/delete-user/**").hasAnyAuthority(ADMIN_DELETE.name())
                        .requestMatchers("/admin/show-users").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers("/admin/ban-user/**").hasAnyAuthority(ADMIN_BAN.name())
                        .requestMatchers("/admin/unban-user/{userId}**").hasAnyAuthority(ADMIN_BAN.name())
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form->
                        form.loginPage("/login")
                                .defaultSuccessUrl("/")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login?error=true")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                );

        return http.build();
    }
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

    }

}