package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class RecipeController {
    private RecipeService recipeService;
    private UsersRepository usersRepository;

    //This is the service dependency injection
    public RecipeController( RecipeService recipeService, UsersRepository usersRepository ) {
        this.recipeService = recipeService;
        this.usersRepository = usersRepository;
    }

    // Inject dependency when Repository is ready
    @GetMapping("/recipe/search/")
    public String index(){
        return "recipe/index";
    }

    @GetMapping("/recipes")
    public String allRecipes(Model vModel) {
        Iterable<Recipe> allRecipes = recipeService.getAllRecipes();
        vModel.addAttribute("recipes", allRecipes);
        return "/index";
    }

    @GetMapping("/recipe/showRecipe")
    public String show(Model vModel){
        vModel.addAttribute(recipeService.getFullRecipe(1L));
        return "recipe/showRecipe";
    }


    @GetMapping("/recipe/create")
    public String showCreateRecipeForm(Model vModel){
        vModel.addAttribute("recipe", new Recipe());
        return "recipe/create";
    }

    @PostMapping("/recipe/create")
    public String create( Model vModel,
                          @RequestParam(name="instructions_text") String[] instructions,
                          @RequestParam(name="ingredients") String[] ingredients,
                          @RequestParam(name="quantity") String[] quantity,
                          @ModelAttribute Recipe recipe){
        //***hard coded user- remove later***
        User user = usersRepository.findOne(1L);
        recipe.setUser(user);
//        User user = new User("abby", "abby@gmail.com", "abby1");
//    usersRepository.save(user);
//        System.out.println(recipe.isPrivateRecipe());
//    recipe.setUser(user);
    recipeService.createNewRecipe(recipe, instructions, ingredients, quantity);
        return"index";
    }






}
