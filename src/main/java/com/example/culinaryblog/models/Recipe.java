package com.example.culinaryblog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String imageUrl;

    private String timeToMake;

    private String recipeName;

    private String text;

    private String shortDescription;

    @DecimalMin(value = "0", inclusive = true, message = "Rating must be greater than or equal to 0")
    @DecimalMax(value = "5", inclusive = true, message = "Rating must be less than or equal to 5")
    @Column(nullable = true)
    private double rating;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments;

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm")
    private Date publishDate;
}
