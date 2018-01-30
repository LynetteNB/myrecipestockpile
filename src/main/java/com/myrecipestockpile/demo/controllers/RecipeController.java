package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Instruction;
import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RecipeController {

    // Inject dependency when Repository is ready


    // Need to insert the recipe first, so we have the ID number for it

    // Then loop through the instructions to add that id to every one

    // Then loop throug


    // RECIPE TESTING GETMAPING
    @GetMapping("/insertTestRecipe")
    public String insertTestRecipe() {

        System.out.println("insert test recipe has fired");

        // HARD CODED TEST
        User user = new User(1, "steveveve", "steve@steve", "password");

        Recipe newRecipe = new Recipe("recipe name", "description", 10, 15, 3, new Date(), false, user, null);

        List<Instruction> instructions = new ArrayList<>();

        instructions.add(new Instruction("step 1"));
        instructions.add(new Instruction("step 2"));
        instructions.add(new Instruction("step 3"));
        instructions.add(new Instruction("step 4"));

        for (Instruction instruction : instructions) {
            instruction.setRecipe();
        }





        Recipe recipe = new Recipe();







    }
}
