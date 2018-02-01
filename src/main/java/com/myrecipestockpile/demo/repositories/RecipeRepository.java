package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Recipe findByUser(User user);
    List<Recipe> findByDescriptionIsLikeOrTitleIsLike(String term, String term2);

}
