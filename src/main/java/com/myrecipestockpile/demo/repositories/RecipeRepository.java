package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    public List<Recipe> findByTitleOrDescription (String title, String description);
}
