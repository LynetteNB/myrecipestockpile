package com.myrecipestockpile.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecipeController {

    // Inject dependency when Repository is ready
    @GetMapping("/recipe/search")
//    @ResponseBody
    public String index(){
        return "recipe/index";
    }

    @GetMapping("/recipe/showRecipe")
//    @ResponseBody
    public String recipe(){
        return "recipe/showRecipe";
    }



}
