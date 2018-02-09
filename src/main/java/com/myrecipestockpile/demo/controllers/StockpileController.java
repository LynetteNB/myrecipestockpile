package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.Stockpile;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.StockpileService;
import com.myrecipestockpile.demo.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StockpileController {

    private StockpileService stockpileService;
    private UserService userService;
    private RecipeRepository recipeRepository;
    private UsersRepository usersRepository;

    public StockpileController(StockpileService stockpileService, UserService userService, RecipeRepository recipeRepository, UsersRepository usersRepository) {
        this.stockpileService = stockpileService;
        this.userService = userService;
        this.recipeRepository = recipeRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/stockpile/{id}")
    public String show(@PathVariable long id, Model vModel) {
        Stockpile stockpile = stockpileService.findOne(id);
        List<Recipe> recipes = stockpile.getStockpileRecipes();

        // Checking if user profile is that of the logged in user, to decide to show private recipes
        User user = usersRepository.findOne(userService.loggedInUser().getId());
        List<Recipe> usersHR = user.getHeartedRecipes();

        List<Recipe> finalRecipes = new ArrayList<>();

        // filters out private ones
//        for (Recipe recipe : recipes) {
//            if (!recipe.isPrivateRecipe()) {
//                finalRecipes.add(recipe);
//            }
//        }
        // This conditional evaluates 2 thing:
        // 1) whether this stockpile belongs to the logged in user - thus grabbing private recipes or not
        // 2) then if the user has previously liked any of the recipes being loaded
        if (stockpile.getOwner() == user) {
            for (Recipe recipe : recipes) {
                if (usersHR.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", recipes);
        } else {
            // filters out private ones
            for (Recipe recipe : recipes) {
                if (!recipe.isPrivateRecipe()) {
                    finalRecipes.add(recipe);
                }
            }
            for (Recipe recipe : finalRecipes) {
                if (usersHR.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", finalRecipes);
        }
//        vModel.addAttribute("user", requestedUser);

        vModel.addAttribute("stockpile", stockpile);
//        vModel.addAttribute("recipes", recipes);
        return "stockpile/show";
    }

    @GetMapping("/stockpile/new")
    public String showCreateStockpileForm(Model vModel) {
//        Stockpile stockpile = new Stockpile();
        vModel.addAttribute("stockpile", new Stockpile());
        return "stockpile/create";
    }

    //Uncomment the code in this method when authentication is implemented and delete the hardcoded user owner
    @PostMapping("/stockpile/create")
    public String createStockpile(@ModelAttribute Stockpile stockpile) {

        // THIS IS NOT WORKING
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User owner = usersRepository.findOne(currentUser.getId());
        stockpile.setOwner(currentUser);
        stockpileService.save(stockpile);
        return "redirect:/profile/" + stockpile.getOwner().getUsername();
    }

    @GetMapping("/stockpile/{id}/edit")
    public String showEditStockpileForm(Model vModel, @PathVariable long id) {
        Stockpile stockpileToEdit = stockpileService.findOne(id);
//        Stockpile stockpile = stockpileService.findOne(id);
        if (!userService.isOwner(stockpileToEdit.getOwner())) {
            return "redirect:/";
        }
        vModel.addAttribute("stockpile", stockpileToEdit);
        return "stockpile/edit";
    }

    @PostMapping("/stockpile/edit")
    public String updateStockpile(@ModelAttribute Stockpile stockpile) {

        // Stockpile is loosing its owner when form is submitted. This is Hotfix.
        User owner = stockpileService.findOne(stockpile.getId()).getOwner();
        stockpile.setOwner(owner);

        stockpileService.save(stockpile);
        String url = "redirect:/stockpile/" + stockpile.getId();
        System.out.println(url);
        return url;
    }

    @PostMapping("stockpile/{id}/delete")
    public String delete(@PathVariable long id) {
        stockpileService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/stockpile/saveto")
    public String addRecipeToStockpile(
            @RequestParam(name = "stockpileToAddTo") List<Long> stockpileIds,
            @RequestParam(name = "recipeId") Long recipeId) {


//        System.out.println(recipeId + " is the recipe id");
        List<Recipe> recipes = new ArrayList<>();

//        List<Stockpile> populatedStockpile = recipe.getStockpiles();
        for (long stockpileId : stockpileIds) {
            Stockpile sp = stockpileService.findOne(stockpileId);
            Recipe recipe = recipeRepository.findOne(recipeId);
            boolean stockpileAlreadyContainsRecipe = sp.getStockpileRecipes().contains(recipe);
            if (!stockpileAlreadyContainsRecipe) {
                recipes.add(recipeRepository.findOne(recipeId));
                recipes.addAll(sp.getStockpileRecipes());
                sp.setStockpileRecipes(recipes);
                stockpileService.save(sp);
                recipes.clear();
            }

//        System.out.println("stockpile id" + stockpileId);
//            populatedStockpile.add(stockpileService.findOne(stockpileId));
        }

//        recipe.setStockpiles(populatedStockpile);
////        recipeRepository.save(recipe);
//        stockpileRepository.save(populatedStockpile);

        return "redirect:/recipes/show/" + recipeId;
    }

}
