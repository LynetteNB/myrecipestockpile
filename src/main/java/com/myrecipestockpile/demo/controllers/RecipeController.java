package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Recipe;
import com.myrecipestockpile.demo.models.Stockpile;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.RecipeRepository;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.RecipeService;
import com.myrecipestockpile.demo.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RecipeController {
    private RecipeService recipeService;
    private UsersRepository usersRepository;
    private RecipeRepository recipeRepository;
    private UserService userService;

    //This is the service dependency injection
    public RecipeController(RecipeService recipeService, UsersRepository usersRepository, RecipeRepository recipeRepository, UserService userService) {
        this.recipeService = recipeService;
        this.usersRepository = usersRepository;
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @GetMapping("/recipes")
    public String allRecipes(Model vModel) {
        User user;
            List<Recipe> usersHeartedRecipes;
        if (userService.isLoggedIn()) {
            user = usersRepository.findOne(userService.loggedInUser().getId());
            usersHeartedRecipes = user.getHeartedRecipes();
        } else {
            usersHeartedRecipes = new ArrayList<>();
            usersHeartedRecipes.add(new Recipe());
        }
        List<Recipe> allRecipes = recipeService.getAllPublicRecipes();
        for (Recipe recipe : allRecipes) {
            if (usersHeartedRecipes.contains(recipe)) {
                recipe.setHearted(true);
            } else {
                recipe.setHearted(false);
            }
        }
        vModel.addAttribute("recipes", allRecipes);
        return "/recipes/index";
    }


    @GetMapping("/recipes/show/{id}")
    public String show(@PathVariable long id, Model vModel) {
        System.out.println("CONTROLLER RUNNING");
        // get the user form the session
        // use the user repository to go for the user to the database -- findOne(user.getId())
        // pass the user to the template
        Object guest = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user;
        if (guest instanceof String) {
            user = new User();
        } else {
            user = usersRepository.findOne(((User) guest).getId());
        }
        Recipe recipe = recipeService.getFullRecipe(id);

        System.out.println(recipe.getHeartedUsers().size());

        // check if recipe is private and owned by user
        if (recipe.isPrivateRecipe() && user == recipe.getUser()) {
            vModel.addAttribute("isHearted", userService.recipeIsLiked(user, recipe));
            vModel.addAttribute("heartCount", recipeService.recipeHeartCount(recipe));
            vModel.addAttribute("recipe", recipe);
            vModel.addAttribute("user", user);
            return "recipes/show";
        }
        if (recipe.isPrivateRecipe()) {
            return "redirect:/";
        }
        if (userService.isLoggedIn()) {
            // this can't be applied if not logged in
            vModel.addAttribute("isHearted", userService.recipeIsLiked(user, recipe));
        }
        vModel.addAttribute("heartCount", recipeService.recipeHeartCount(recipe));
        vModel.addAttribute("recipe", recipe);
        vModel.addAttribute("user", user);
        return "recipes/show";
    }


    @GetMapping("/recipes/create")
    public String showCreateRecipeForm(Model vModel) {
        vModel.addAttribute("recipe", new Recipe());
        return "recipes/create";
    }

    @PostMapping("/recipes/create")
    public String create(Model vModel,
                         @RequestParam(name = "instructions_text") String[] instructions,
                         @RequestParam(name = "ingredients") String[] ingredients,
                         @RequestParam(name = "quantity") String[] quantity,
                         @RequestParam(name = "image") String image,
                         @ModelAttribute Recipe recipe) {
        System.out.println(recipe.getImageUrl());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(usersRepository.findOne(user.getId()));
        if (image.isEmpty()) {
            recipe.setImageUrl("/images/default-recipe-img.jpg");
        } else {
            recipe.setImageUrl(image);
        }
        recipeService.createNewRecipe(recipe, instructions, ingredients, quantity);
        return "redirect:/profile";
    }

    @GetMapping("/recipes/edit/{id}")
    public String showEditRecipeForm(@PathVariable long id, Model vModel) {
        Recipe recipe = recipeService.getFullRecipe(id);
        if (!userService.isOwner(recipe.getUser())) {
            return "redirect:/";
        }
        vModel.addAttribute(recipe);
        return "recipes/edit";
    }

    @PostMapping("/recipes/edit")
    public String updatePost(Model vModel,
                             @RequestParam(name = "instructions_text") String[] instructions,
                             @RequestParam(name = "ingredients") String[] ingredients,
                             @RequestParam(name = "quantity") String[] quantity,
                             @RequestParam(name = "image") String image,
                             @ModelAttribute Recipe recipe) {
        User user = recipeService.getFullRecipe(recipe.getId()).getUser();
        recipe.setUser(user);

        if (!image.equalsIgnoreCase("")) {
            recipe.setImageUrl(image);
        } else {
            String img = recipeService.getFullRecipe(recipe.getId()).getImageUrl();
            System.out.println(img + " is stored img");
            recipe.setImageUrl(img);
        }

        System.out.println(image + "is parameter img");
        recipeService.editRecipe(recipe, instructions, ingredients, quantity);
        return "redirect:/recipes/show/" + recipe.getId();
    }


    @PostMapping("/recipes/delete")
    public String delete(@RequestParam("id") long id) {
        Recipe recipe = recipeRepository.findOne(id);
        recipeService.deleteRecipe(recipe);
        return "redirect:/";
    }


    @GetMapping("/recipes/search")
    public String search(@RequestParam(name = "term") String term, Model vModel) {
        String termOG = term;
        term = "%" + term + "%";

        List<Recipe> results = recipeRepository.findByDescriptionIsLikeAndPrivateRecipeOrTitleIsLikeAndPrivateRecipe(term, false, term, false);

        User user;
        List<Recipe> usersHeartedRecipes;
        if (userService.isLoggedIn()) {
            user = usersRepository.findOne(userService.loggedInUser().getId());
            usersHeartedRecipes = user.getHeartedRecipes();
        } else {
            usersHeartedRecipes = new ArrayList<>();
            usersHeartedRecipes.add(new Recipe());
        }
        for (Recipe recipe : results) {
            if (usersHeartedRecipes.contains(recipe)) {
                recipe.setHearted(true);
            } else {
                recipe.setHearted(false);
            }
        }
        vModel.addAttribute("searchTerm", termOG);
        vModel.addAttribute("recipes", results);
        return "recipes/results";
    }

    @GetMapping("/recipes/variation/{id}")
    public String showVariationForm(@PathVariable long id, Model vModel) {
        Recipe recipe = recipeService.getFullRecipe(id);
        vModel.addAttribute("recipe", recipe);
        return "recipes/variation";
    }

    @PostMapping("/recipes/create-variation")
    public String createRecipeVariation(Model vModel,
                                        @RequestParam(name = "instructions_text") String[] instructions,
                                        @RequestParam(name = "ingredients") String[] ingredients,
                                        @RequestParam(name = "quantity") String[] quantity,
                                        @RequestParam(name = "image") String image,
                                        @ModelAttribute Recipe recipe) {
        System.out.println(image + "this is the image url");
        Recipe parentRecipe = recipeRepository.findOne(recipe.getId());
        Recipe newVariant = new Recipe(
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getCookTime(),
                recipe.getPrepTime(),
                recipe.getServings(),
                recipe.isPrivateRecipe(),
                recipe.getUser(),
                parentRecipe
        );
        if (!image.equalsIgnoreCase("")) {
            newVariant.setImageUrl(image);
        } else {
            newVariant.setImageUrl(parentRecipe.getImageUrl());
        }


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newVariant.setUser(user);

        recipe.setParentRecipe(recipeRepository.findOne(recipe.getId()));
        long id = recipeService.createNewRecipe(newVariant, instructions, ingredients, quantity);


        return "redirect:/recipes/show/" + id;
    }

    @PostMapping("/heart-update")
    public @ResponseBody long processAJAXRequest(
            @RequestParam("userId") long userId,
            @RequestParam("recipeId") long recipeId) {
        System.out.println("recipe id = " + recipeId + " | " + "user id = " + userId);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser == null) {
            return 0-1;
        } else if (userId != loggedInUser.getId()) {
            return 0-1;
        } else {
            userService.updateHeart(userId, recipeId);
        }
        return recipeService.getFullRecipe(recipeId).getHeartedUsers().size();
    }

    @GetMapping("/stockpile/allMyHeartedRecipes/{username}")
    public String viewUsersHeartedRecipes(@PathVariable String username, Model vModel) {
        User user = usersRepository.findByUsername(username);
        List<Recipe> heartedRecipes = user.getHeartedRecipes();
        if (user.getId() == userService.loggedInUser().getId()) {
            for (Recipe recipe : heartedRecipes) {
                recipe.setHearted(true);
            }
            vModel.addAttribute("recipes", heartedRecipes);
        } else {
            List<Recipe> publicHeartedRecipes = new ArrayList<>();
            for (Recipe recipe : heartedRecipes) {
                if (!recipe.isPrivateRecipe()) {
                    publicHeartedRecipes.add(recipe);
                }
            }
            User currentUser = usersRepository.findOne(userService.loggedInUser().getId());
            List<Recipe> currentHearts = currentUser.getHeartedRecipes();
            for (Recipe recipe : publicHeartedRecipes) {
                if (currentHearts.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", publicHeartedRecipes);

        }
        vModel.addAttribute("user", user);
        vModel.addAttribute("title", "Hearted Recipes");
        return "/recipes/index";
    }

    @GetMapping("/stockpile/allMyRecipes/{username}")
    public String viewAllUsersRecipes(@PathVariable String username, Model vModel) {
        User user = usersRepository.findByUsername(username);
        User loggedInUser = usersRepository.findOne(userService.loggedInUser().getId());
        List<Recipe> currentHearts = loggedInUser.getHeartedRecipes();
        if (user.getId() == userService.loggedInUser().getId()) {
            List<Recipe> allRecipes = recipeRepository.findByUser(user);
            for (Recipe recipe : allRecipes) {
                if (currentHearts.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", allRecipes);
        } else {
            List<Recipe> allPublicRecipes = recipeRepository.findByUserAndPrivateRecipe(user, false);
            for (Recipe recipe : allPublicRecipes) {
                if (currentHearts.contains(recipe)) {
                    recipe.setHearted(true);
                } else {
                    recipe.setHearted(false);
                }
            }
            vModel.addAttribute("recipes", allPublicRecipes);
        }
        vModel.addAttribute("title", "All My Recipes");
        vModel.addAttribute("user", user);
        return "/recipes/index";
    }
}