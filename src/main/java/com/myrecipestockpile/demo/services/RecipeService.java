package com.myrecipestockpile.demo.services;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.repositories.IngredientsRepository;
import com.myrecipestockpile.demo.repositories.RecipeIngredientsRepository;
import com.myrecipestockpile.demo.repositories.RecipeInstructionsRepository;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import org.springframework.stereotype.Service;


@Service
public class RecipeService {
    private RecipeRepository recipeRepository;
    private RecipeInstructionsRepository recipeInstructionsRepository;
    private RecipeIngredientsRepository recipeIngredientsRepository;
    private IngredientsRepository ingredientsRepository;

    // CRUD Dependency injection.
    public RecipeService(RecipeRepository recipeRepository, RecipeInstructionsRepository recipeInstructionsRepository, RecipeIngredientsRepository recipeIngredientsRepository, IngredientsRepository ingredientsRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeInstructionsRepository = recipeInstructionsRepository;
        this.recipeIngredientsRepository = recipeIngredientsRepository;
        this.ingredientsRepository = ingredientsRepository;
    }

//    --------------------------- Empty method skeletons. Bodies will be added in next update.

    // This method will insert a new recipe into database and return the id of that recipe.
    public long createNewRecipe(Recipe recipe,
                                String[] instuctionsArray,
                                String[] ingredientNameArray,
                                String[] ingredientQuantityArray) {
        return 1;
    }

    // This method will get a full recipe with by id. It will include ingredients, quantities, and instructions in the Recipe object.
    public Recipe getFullRecipe(long id) {

        return new Recipe();
    }

    // This method will take in a an edited recipe and update it in the database.
    public long editRecipe(Recipe recipe,
                           String[] instuctionsArray,
                           String[] ingredientNameArray,
                           String[] ingredientQuantityArray) {

        return 1;
    }



}
