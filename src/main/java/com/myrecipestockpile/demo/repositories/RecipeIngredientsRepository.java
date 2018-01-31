package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.RecipeIngredient;
import org.springframework.data.repository.CrudRepository;

public interface RecipeIngredientsRepository extends CrudRepository<RecipeIngredient, Long> {
}
