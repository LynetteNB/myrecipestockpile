package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.ProfileService;
import com.myrecipestockpile.demo.services.RecipeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    private ProfileService profileService;
    private RecipeService recipeService;
    private UsersRepository usersRepository;

    public ProfileController(ProfileService profileService, RecipeService recipeService, UsersRepository usersRepository) {
        this.profileService = profileService;
        this.recipeService = recipeService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/profile/{username}")
    public String showProfile(@PathVariable String username, Model vModel) {

        User requestedUser = profileService.findUsername(username);

        // If requested is null or empty
        if (username.equalsIgnoreCase("") || requestedUser == null) {
            return "redirect:/";
        }
        // Checking if user profile is that of the logged in user, to decide to show private recipes
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Recipe> usersHR = usersRepository.findOne(user.getId()).getHeartedRecipes();

        List<Recipe> profileRecipes = new ArrayList<>();
        // This conditional evaluates 2 thing:
        // 1) whether this profile belongs to the logged in user - thus grabbing private recipes or not
        // 2) then if the user has previously liked any of the recipes being loaded
        if (requestedUser.getId() == user.getId()) {
            profileRecipes = recipeService.showUsersThreeMostRecentPublicAndPrivate(requestedUser);
            for (Recipe recipe : profileRecipes) {
                if (usersHR.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", profileRecipes);
        } else {
            profileRecipes = recipeService.showUsersThreeMostRecentPublic(requestedUser);
            for (Recipe recipe : profileRecipes) {
                if (usersHR.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", profileRecipes);
        }
        vModel.addAttribute("user", requestedUser);
        return "profile";
    }

    @GetMapping("/profile")
    public String showCurrentUserProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "redirect:/profile/" + user.getUsername();
    }

}
