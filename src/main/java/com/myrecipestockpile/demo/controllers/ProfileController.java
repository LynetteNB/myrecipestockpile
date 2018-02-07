package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.services.ProfileService;
import com.myrecipestockpile.demo.services.RecipeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private ProfileService profileService;
    private RecipeService recipeService;

    public ProfileController(ProfileService profileService, RecipeService recipeService) {
        this.profileService = profileService;
        this.recipeService = recipeService;
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
        if (requestedUser.getId() == user.getId()) {
            vModel.addAttribute("recipes", recipeService.showUsersFourMostRecentPublicAndPrivate(requestedUser));
        } else {
            vModel.addAttribute("recipes", recipeService.showUsersFourMostRecentPublic(requestedUser));
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
