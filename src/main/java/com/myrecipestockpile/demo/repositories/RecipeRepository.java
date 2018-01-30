package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
