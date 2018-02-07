package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.RecipeService;
import com.myrecipestockpile.demo.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserService userService;

    //This is the service dependency injection
    public RecipeController(RecipeService recipeService, UsersRepository usersRepository, RecipeRepository recipeRepository, UserService userService) {
        this.recipeService = recipeService;
        this.usersRepository = usersRepository;
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @GetMapping("/recipes")
    public String allRecipes(Model vModel) {
        Iterable<Recipe> allRecipes = recipeService.getAllRecipes();
        vModel.addAttribute("recipes", allRecipes);
        return "/index";
    }


    @GetMapping("/recipes/show/{id}")
    public String show(@PathVariable long id, Model vModel) {
        vModel.addAttribute(recipeService.getFullRecipe(id));
        return "recipes/show";
    }


    @GetMapping("/recipes/create")
    public String showCreateRecipeForm(Model vModel) {
        vModel.addAttribute("recipe", new Recipe());
        return "recipes/create";
    }

    @PostMapping("/recipes/create")
    public String create(Model vModel,
                         @RequestParam(name = "instructions_text") String[] instructions,
                         @RequestParam(name = "ingredients") String[] ingredients,
                         @RequestParam(name = "quantity") String[] quantity,
                         @RequestParam(name = "image") String image,
                         @ModelAttribute Recipe recipe) {
        System.out.println(recipe.getImageUrl());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(usersRepository.findOne(user.getId()));
        recipe.setImageUrl(image);
        recipeService.createNewRecipe(recipe, instructions, ingredients, quantity);
        return "index";
    }

    @GetMapping("/recipes/edit/{id}")
    public String showEditRecipeForm(@PathVariable long id, Model vModel) {
        Recipe recipe = recipeService.getFullRecipe(id);
        if (!userService.isOwner(recipe.getUser())) {
            return "redirect:/";
        }
        vModel.addAttribute(recipe);
        return "recipes/edit";
    }

    @PostMapping("/recipes/edit")
    public String updatePost(Model vModel,
                             @RequestParam(name = "instructions_text") String[] instructions,
                             @RequestParam(name = "ingredients") String[] ingredients,
                             @RequestParam(name = "quantity") String[] quantity,
                             @RequestParam(name = "image") String image,
                             @ModelAttribute Recipe recipe) {
        User user = recipeService.getFullRecipe(recipe.getId()).getUser();
        recipe.setUser(user);
        recipe.setImageUrl(image);
        System.out.println(image);
        recipeService.editRecipe(recipe, instructions, ingredients, quantity);
        return "redirect:/recipes/show/" + recipe.getId();
    }


    @PostMapping("/recipes/delete")
    public String delete(Model vModel,
                         @ModelAttribute Recipe recipe) {
        recipeService.deleteRecipe(recipe);
        return "redirect:/";
    }


    @GetMapping("/recipes/search")
    public String search(@RequestParam(name = "term") String term, Model vModel) {
        term = "%" + term + "%";
        vModel.addAttribute("recipes", recipeRepository.findByDescriptionIsLikeOrTitleIsLike(term, term));
        return "recipes/results";
    }

    @GetMapping("/recipes/variation/{id}")
    public String showVariationForm(@PathVariable long id, Model vModel) {
        Recipe recipe = recipeService.getFullRecipe(id);
        vModel.addAttribute("recipe", recipe);
        return "recipes/variation";
    }

    @PostMapping("/recipes/create-variation")
    public String createRecipeVariation(Model vModel,
                                        @RequestParam(name = "instructions_text") String[] instructions,
                                        @RequestParam(name = "ingredients") String[] ingredients,
                                        @RequestParam(name = "quantity") String[] quantity,
                                        @RequestParam(name = "image") String image,
                                        @ModelAttribute Recipe recipe) {
        Recipe parentRecipe = recipeRepository.findOne(recipe.getId());
        Recipe newVariant = new Recipe(
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getCookTime(),
                recipe.getPrepTime(),
                recipe.getServings(),
                recipe.isPrivateRecipe(),
                recipe.getUser(),
                parentRecipe
        );
        newVariant.setImageUrl(image);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newVariant.setUser(user);

        recipe.setParentRecipe(recipeRepository.findOne(recipe.getId()));
        long id = recipeService.createNewRecipe(newVariant, instructions, ingredients, quantity);


        return "redirect:/recipes/show/" + id;
    }

//    ***Hearted Recipes****
    @GetMapping("/recipes/{id}/like")
    public String heartedRecipes(@PathVariable long id,  Model vModel){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recipe recipe = recipeRepository.findOne(id);
        user = usersRepository.findOne(user.getId());
        List<Recipe> heartedRecipes = user.getHeartedRecipes();
        heartedRecipes.add(recipe);
        user.setHeartedRecipes(heartedRecipes);
        usersRepository.save(user);
        return "redirect:/recipes";
    }

}
