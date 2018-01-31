package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.*;
import com.myrecipestockpile.demo.repositories.IngredientRepository;
import com.myrecipestockpile.demo.repositories.InstructionRepository;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RecipeController {
    private RecipeRepository recipeRepository;
    private UsersRepository usersRepository;
    private InstructionRepository instructionRepository;
    private IngredientRepository ingredientRepository;

    // Inject dependency when Repository is ready

    public RecipeController(RecipeRepository recipeRepository, UsersRepository usersRepository, InstructionRepository instructionRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.usersRepository = usersRepository;
        this.instructionRepository = instructionRepository;
        this.ingredientRepository = ingredientRepository;
    }


    // Need to insert the recipe first, then grab that recipe object.

    // Then loop through the instructions to add that object to every to every one

    // Then loop


    // RECIPE TESTING GETMAPING
    @GetMapping("/insertTestRecipe")
    public String insertTestRecipe() {

        System.out.println("insert test recipe has fired");



        // HARD CODED TEST
        ingredientRepository.save(new Ingredient("peaches"));
        ingredientRepository.save(new Ingredient("eggs"));
        ingredientRepository.save(new Ingredient("lemons"));

        // Inserting and using a TEST User
        usersRepository.save(new User("steveveve", "steve@steve", "password"));
        User user = usersRepository.findOne((long) 1);

//        System.out.println(usersRepository.findByUsernameLike("ev").getUsername());


        // Primary fields needed from Recipe form to insert into Recipe table.
        Recipe newRecipe = new Recipe(
                "recipe name",
                "description",
                10,
                15,
                3,
                new Date(),
                false,
                user,
                null
        );

        newRecipe = recipeRepository.save(newRecipe);

        // An empty Array to capture multiple inputs from form.
        List<String> rawInstructions = new ArrayList<>();

        // Simulated array from form.
        rawInstructions.add("step 1");
        rawInstructions.add("step 2");
        rawInstructions.add("step 3");
        rawInstructions.add("step 4");
        rawInstructions.add("step 5");

        // Integrating over that array to create Instruction objects with each and saving them with the newRecipe object so they have ownership attached to them.
        for (String instruction : rawInstructions) {
            instructionRepository.save(new Instruction(instruction, newRecipe));
        }


        List<String> rawQuanitities = new ArrayList<>();
        List<String> rawIngredients = new ArrayList<>();

        rawQuanitities.add("2");
        rawQuanitities.add("6");
        rawQuanitities.add("11");

        rawIngredients.add("peaches");
        rawIngredients.add("lemons");
        rawIngredients.add("peaches");

//        for (String quantity : rawQuanitities) {
//            ingredientRepository.
//        }
////
//        recipeRepository.findByTitleOrDescription("search term", "search term");
//
//        for (String ingredient : rawIngredients) {
//            if (ingredientRepository.findA)
//        }





        Recipe recipe = new Recipe();




    return "redirect:/";

    }
}
