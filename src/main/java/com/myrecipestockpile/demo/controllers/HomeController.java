package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.RecipeService;
import com.myrecipestockpile.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private RecipeService recipeService;
    private UserService userService;
    private UsersRepository usersRepository;


    public HomeController(RecipeService recipeService, UserService userService, UsersRepository usersRepository) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String index(Model vModel) {
        List<Recipe> allRecipes = recipeService.getRecentAllSix();
        User user;
        List<Recipe> usersHeartedRecipes;

        if (userService.isLoggedIn()) {
            user = usersRepository.findOne(userService.loggedInUser().getId());
            usersHeartedRecipes = user.getHeartedRecipes();
        } else {
            usersHeartedRecipes = new ArrayList<>();
            usersHeartedRecipes.add(new Recipe());
        }
        for (Recipe recipe : allRecipes) {
            if (usersHeartedRecipes.contains(recipe)) {
                recipe.setHearted(true);
            } else {
                recipe.setHearted(false);
            }
        }

        vModel.addAttribute("recipes", allRecipes);
        return "index";
    }


//    @GetMapping("/login")
//    public String loginForm(Model vModel) {
//        vModel.addAttribute("user", new User());
//        return "login";
//    }

    //    @PostMapping("/login")
//    public String loginSumbmission() {
//        return "index";
//    }
//
//    // temporary until security implementation
//    @PostMapping("/logout")
//    public String logout() {
//        return "index";
//    }
//
//    @PostMapping("/sign-up")
//    public String saveUser(@ModelAttribute User user) {
//        // this is a simple check for existing users by username and email to avoid errors.
//        User userCheck = usersRepository.findByUsername(user.getUsername());
//        if (userCheck == null) {
//            usersRepository.save(user);
//            return "login";
//        } else {
//            System.out.println("already entity");
//            return "login";
//        }
//    }
//
//    @GetMapping("/sign-up")
//    public String showSignUpForm(Model vModel) {
//        vModel.addAttribute("user", new User());
//        return "signup";
//
//    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}


