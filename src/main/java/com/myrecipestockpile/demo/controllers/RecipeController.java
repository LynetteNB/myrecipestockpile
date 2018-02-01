package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
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
    private RecipeRepository recipeRepository;

    //This is the service dependency injection
    public RecipeController( RecipeService recipeService, UsersRepository usersRepository, RecipeRepository recipeRepository ) {
        this.recipeService = recipeService;
        this.usersRepository = usersRepository;
        this.recipeRepository = recipeRepository;
    }

    // Inject dependency when Repository is ready
    @GetMapping("/recipes/search/")
    public String index(){
        return "recipes/index";
    }

    @GetMapping("/recipes")
    public String allRecipes(Model vModel) {
        Iterable<Recipe> allRecipes = recipeService.getAllRecipes();
        vModel.addAttribute("recipes", allRecipes);
        return "/index";
    }

    @GetMapping("/recipes/show")
    public String show(Model vModel){
        vModel.addAttribute(recipeService.getFullRecipe(1L));
        return "recipes/show";
    }


    @GetMapping("/recipes/create")
    public String showCreateRecipeForm(Model vModel){
        vModel.addAttribute("recipe", new Recipe());
        return "recipes/create";
    }

    @PostMapping("/recipes/create")
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

    @PostMapping("/recipes/search")
    public String search(@RequestParam(name = "term") String term, Model vModel){
        term = "%"+term+"%";
        vModel.addAttribute("recipes", recipeRepository.findByDescriptionIsLikeOrTitleIsLike(term, term));
        return "recipes/results";
    }
}
