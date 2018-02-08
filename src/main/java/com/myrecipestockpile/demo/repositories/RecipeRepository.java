package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Iterable<Recipe> findAllByPrivateRecipe(boolean isPrivate);

    Iterable<Recipe> findByUserAndPrivateRecipe(User user, boolean isPrivate);
    Iterable<Recipe> findByUser(User user);

//    List<Recipe> findByDescriptionIsLikeOrTitleIsLike(String term, String term2);
    List<Recipe> findByDescriptionIsLikeAndPrivateRecipeOrTitleIsLikeAndPrivateRecipe(String term, boolean isPrivate1, String term2, boolean isPrivate2);

    // To get a list of 6 publicly visible recipes
    List<Recipe> findFirst6ByPrivateRecipeOrderByDateCreated(boolean isPrivate);

    //  Version for Public and Private recipes
    List<Recipe> findFirst4ByUserOrderByDateCreatedDesc(User user);
    // Users 4 most recent recipes publicly visible.
    List<Recipe> findFirst4ByUserAndPrivateRecipeOrderByDateCreatedDesc(User user, boolean isPrivate);
}
