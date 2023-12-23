package com.example.culinaryblog.repositories;

import com.example.culinaryblog.models.Ingredient;
import com.example.culinaryblog.models.Recipe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
   //     List<Recipe> findAll(PageRequest pageable);


}
