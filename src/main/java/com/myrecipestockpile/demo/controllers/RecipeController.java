package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class RecipeController {

    // Inject dependency when Repository is ready
    @GetMapping("/recipe/search/")
    public String index(){
        return "recipe/index";
    }

    @GetMapping("/recipe/showRecipe")
    public String show(){
        return "recipe/showRecipe";
    }


    @GetMapping("/recipe/create")
    public String showCreateRecipeForm(Model vModel){
        vModel.addAttribute("recipe", new Recipe());
        return "recipe/create";
    }

    @PostMapping("/recipe/create")
    public String create( Model vModel, @RequestParam(name="instructions") String[] instructions){
        System.out.println(Arrays.toString(instructions));

        return"recipe/create";
    }



}
