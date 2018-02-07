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

        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = recipeRepository.findOne(recipeId);
        recipes.add(recipe);
        User user = usersRepository.findOne(userId);

        // This will return user if the user/recipe pair exists on the hearted_recipes table
        // Or it will return 'null' if there the pair doesn't exist
        User userOrNullUser = usersRepository.findByHeartedRecipes(recipes);

        if (userOrNullUser != null) {
            List<Recipe> heartedRecipes = user.getHeartedRecipes();
            System.out.println(heartedRecipes.size() + " before delete");
            heartedRecipes.remove(recipe);
            user.setHeartedRecipes(heartedRecipes);
            usersRepository.save(user);
            System.out.println(heartedRecipes.size() + " after delete");
        } else {
        recipes.addAll(user.getHeartedRecipes());
        user.setHeartedRecipes(recipes);
        usersRepository.save(user);
        recipes.clear();
            System.out.println("save");

        }
    }

    public boolean recipeIsLiked(User user, Recipe recipe) {
        return user.getHeartedRecipes().contains(recipe);
    }
}
