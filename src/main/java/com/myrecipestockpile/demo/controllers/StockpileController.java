package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.Stockpile;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import com.myrecipestockpile.demo.repositories.StockpileRepository;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.StockpileService;
import com.myrecipestockpile.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class StockpileController {

    private StockpileService stockpileService;
    private UsersRepository usersRepository;
    private UserService userService;
    private RecipeRepository recipeRepository;
    private StockpileRepository stockpileRepository;

    public StockpileController(StockpileService stockpileService, UsersRepository usersRepository, UserService userService, RecipeRepository recipeRepository, StockpileRepository stockpileRepository) {
        this.stockpileService = stockpileService;
        this.usersRepository = usersRepository;
        this.userService = userService;
        this.recipeRepository = recipeRepository;
        this.stockpileRepository = stockpileRepository;
    }

    @GetMapping("/stockpile/{id}")
    public String show(@PathVariable long id, Model vModel) {
        Stockpile stockpile = stockpileService.findOne(id);
        vModel.addAttribute("stockpile", stockpile);
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


        System.out.println(recipeId + " is the recipe id");
        List<Recipe> recipes = new ArrayList<>();

//        List<Stockpile> populatedStockpile = recipe.getStockpiles();
        for (long stockpileId : stockpileIds) {
            Stockpile sp = stockpileService.findOne(stockpileId);
            recipes.add(recipeRepository.findOne(recipeId));
            recipes.addAll(sp.getStockpileRecipes());
            sp.setStockpileRecipes(recipes);
            stockpileService.save(sp);
            recipes.clear();

//        System.out.println("stockpile id" + stockpileId);
//            populatedStockpile.add(stockpileService.findOne(stockpileId));
        }

//        recipe.setStockpiles(populatedStockpile);
////        recipeRepository.save(recipe);
//        stockpileRepository.save(populatedStockpile);

        return "redirect:/";
    }

}
