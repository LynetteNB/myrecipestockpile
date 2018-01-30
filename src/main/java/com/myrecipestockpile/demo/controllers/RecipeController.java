package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Ingredient;
import com.myrecipestockpile.demo.models.Instruction;
import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
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
        usersRepository.save(new User(1, "steveveve", "steve@steve", "password"));

        User user = usersRepository.findOne((long) 1);

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


        List<Instruction> instructions = new ArrayList<>();

        instructions.add(new Instruction("step 1"));
        instructions.add(new Instruction("step 2"));
        instructions.add(new Instruction("step 3"));
        instructions.add(new Instruction("step 4"));

        for (Instruction instruction : instructions) {
            instruction.setRecipe(newRecipe);
            instructionRepository.save(instruction);
        }




        Recipe recipe = new Recipe();




    return "redirect:/";

    }
}
