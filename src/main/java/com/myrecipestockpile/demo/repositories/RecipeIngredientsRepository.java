package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.RecipeIngredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeIngredientsRepository extends CrudRepository<RecipeIngredient, Long> {
    public List<RecipeIngredient> findByRecipe(Recipe recipe);
}
