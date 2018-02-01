package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    private UsersService usersService;

    public ProfileController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("profile/{username}")
    public String showProfile(@PathVariable String username, Model vModel) {
        vModel.addAttribute("user", usersService.getUser(username));
        return "profile";
    }

}
