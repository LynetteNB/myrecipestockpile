package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@Controller
public class HomeController {
    private UsersRepository usersRepository;

    public HomeController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String index() {
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
}


