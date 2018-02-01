package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.services.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile/{username}")
    public String showProfile(@PathVariable String username, Model vModel) {

        User requestedUser = profileService.findUsername(username);

        // If requested
        if (username.equalsIgnoreCase("") || requestedUser == null) {
            return "redirect:/";
        } else {

            System.out.println(requestedUser.getUsername());
            vModel.addAttribute("user", requestedUser);
            return "profile";
        }
    }

}
