package com.example.culinaryblog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JoinColumn(name="recipe_id", nullable=false)
    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id", nullable = true)
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private Set<Comment> childComments;

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm")
    private Date publishDate;
}
