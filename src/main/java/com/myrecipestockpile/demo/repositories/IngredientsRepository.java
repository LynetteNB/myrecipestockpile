package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientsRepository extends CrudRepository<Ingredient, Long> {
}
