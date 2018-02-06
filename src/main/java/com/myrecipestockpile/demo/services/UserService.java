package com.myrecipestockpile.demo.services;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UsersRepository usersRepository;
    private RecipeRepository recipeRepository;

    public UserService(UsersRepository usersRepository, RecipeRepository recipeRepository) {
        this.usersRepository = usersRepository;
        this.recipeRepository = recipeRepository;
    }

    public boolean isLoggedIn() {
        boolean isAnonymousUser =
                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return !isAnonymousUser;
    }

    public User loggedInUser() {
        if (!isLoggedIn()) {
            return null;
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // Check if user is the owner of the recipe
    public boolean isOwner(User user) {
        if (isLoggedIn()) {
            return (user.getUsername().equals(loggedInUser().getUsername()));
        }

        return false;

    }

    public void updateHeart(long userId, long recipeId) {
//        Recipe recipe = recipeRepository.findOne(recipeId);


        List<Recipe> recipes = new ArrayList<>();
//        Stockpile sp = stockpileService.findOne(stockpileId);
        User user = usersRepository.findOne(userId);
        recipes.add(recipeRepository.findOne(recipeId));

        recipes.addAll(user.getHeartedRecipes());
        user.setHeartedRecipes(recipes);
        usersRepository.save(user);
        recipes.clear();

    }
}
