package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Recipe findByUser(User user);
    List<Recipe> findByDescriptionIsLikeOrTitleIsLike(String term, String term2);

    // To get a list of 6 publicly visible recipes
    List<Recipe> findFirst6ByPrivateRecipeOrderByDateCreated(boolean isPrivate);

    //  Version for Public and Private recipes
    List<Recipe> findFirst4ByUserOrderByDateCreated(User user);
    // Users 4 most recent recipes publicly visible.
    List<Recipe> findFirst4ByUserAndPrivateRecipeOrderByDateCreated(User user, boolean isPrivate);
}
