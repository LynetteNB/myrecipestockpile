package com.myrecipestockpile.demo.services;

import com.myrecipestockpile.demo.models.Ingredient;
import com.myrecipestockpile.demo.models.Instruction;
import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.RecipeIngredient;
import com.myrecipestockpile.demo.repositories.IngredientsRepository;
import com.myrecipestockpile.demo.repositories.RecipeIngredientsRepository;
import com.myrecipestockpile.demo.repositories.RecipeInstructionsRepository;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
                                String[] instructionsArray,
                                String[] ingredientNameArray,
                                String[] ingredientQuantityArray) {
        // Initially save the core recipe, so it has an id generated with it. Otherwise a transient error happens.
        recipeRepository.save(recipe);

        List<Instruction> instructions = new ArrayList<>();
//
        // Saving ingredients.

        // Check if each ingredient exists before adding it to database with quantity.
        for (int i = 0; i < ingredientNameArray.length && i < ingredientQuantityArray.length; i += 1) {
            String newIngredient = ingredientNameArray[i];
            String newQuantity = ingredientQuantityArray[i];

            // Query for new Ingredient. Unknown at moment if it already exists on 'ingredients' table.
            Ingredient existingIngredient = ingredientsRepository.findByIngredient(newIngredient);

            // Checking if Query returned an existing Ingredient that matches the user's String input.
            if (existingIngredient == null) {
                // Creates the new Ingredient if none is found.
                ingredientsRepository.save(new Ingredient(newIngredient));
            }

            // Grabbing a valid Ingredient from table to be certain for recipe.
            Ingredient readyIngredient = ingredientsRepository.findByIngredient(newIngredient);

            // Saving final RecipeIngredient to 'recipe_ingredients' table.
            recipeIngredientsRepository.save(new RecipeIngredient(recipe, newQuantity, readyIngredient));
        }

        // Creates List of new Instruction objects to set to Recipe.
        for (String instruction : instructionsArray) {
            instructions.add(new Instruction(instruction, recipe));
        }
        recipe.setInstructions(instructions);

        // Final save. Updates the recipe, but adding data to dependent tables.
        recipeRepository.save(recipe);
        return recipe.getId();
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
