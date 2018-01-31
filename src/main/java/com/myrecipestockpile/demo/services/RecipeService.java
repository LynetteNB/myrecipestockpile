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

        // Saving new Recipe to 'recipes' table.
        recipeRepository.save(recipe);

        // Saving each new Recipe's instruction to 'instructions' table.
        for (String instruction : instuctionsArray) {
            recipeInstructionsRepository.save(new Instruction(instruction, recipe));
        }

        // Saving ingredients.

        //Check if ingredient exists. If yes, then at. if not, save and then get id
        for (int i = 0; i < ingredientNameArray.length && i < ingredientQuantityArray.length; i += 0) {
            String newIngredient = ingredientNameArray[i];
            String newQuantity = ingredientQuantityArray[i];

            // Query for new Ingredient. Unknown at moment if it already exists on 'ingredients' table.
            Ingredient existingIngredient = ingredientsRepository.findByIngredient(newIngredient);

            // TESTING SOUTS
            System.out.println(existingIngredient);
            System.out.println(existingIngredient.getIngredient());

            // Checking if Query returned an existing Ingredient that matches the user's String input.
            // Creates the new Ingredient if none is found.
            if (!existingIngredient.getIngredient().equalsIgnoreCase(newIngredient)) {
                ingredientsRepository.save(new Ingredient(newIngredient));
            }

            // Grabbing a valid Ingredient from table to be certain.
            Ingredient readyIngredient = ingredientsRepository.findByIngredient(newIngredient);

            // Saving final RecipeIngredient to 'recipe_ingredients' table.
            recipeIngredientsRepository.save(new RecipeIngredient(recipe, newQuantity, readyIngredient));



        }


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
