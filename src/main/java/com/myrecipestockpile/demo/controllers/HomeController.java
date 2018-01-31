package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    private UsersRepository usersRepository;

    public HomeController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
//    @ResponseBody
    public String index() {
        return "index";
    }

    @PostMapping("/sign-up")
    public String saveUser( @ModelAttribute User user){
        usersRepository.save(user);
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpForm( Model vModel){
        vModel.addAttribute("user", new User());
        return "signup";

    }
}


