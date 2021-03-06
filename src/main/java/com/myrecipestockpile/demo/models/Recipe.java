package com.myrecipestockpile.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    // Cook time should be in unit of 'minutes'.
    @Column(nullable = false)
    private int cookTime;

    // Prep time should be in unit of 'minutes'.
    @Column(nullable = false)
    private int prepTime;

    @Column(nullable = false)
    private int servings;

    @Column(nullable = false)
    private Date dateCreated;

    // Privacy is set to false by default
    @Column(nullable = false)
    private boolean privateRecipe = false;

    @Column
    private String imageUrl;

    // Connects to users table, it represents it's owner.
    @ManyToOne
    @JsonIgnore
    private User user;

    // Connects to another recipe's id, it represents the recipe which this one is a variation of. If null, it has no parent recipe and is not a variation.
    @OneToOne
    @JsonIgnore
    private Recipe parentRecipe;

    // Connects to recipe_ingredient table. It will hold a list of all the recipe ingredients connected to this recipe.
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RecipeIngredient> recipeIngredients;

    // Connects to instructions table, to show which instructions belong to it. Instructions sorted by primary key will show proper order.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @JsonIgnore
    private List<Instruction> instructions;


    // Connects to stockpile table. The pivot table formed shows recipe/stockpile
    @ManyToMany(mappedBy = "stockpileRecipes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Stockpile> stockpiles;

    // Connects to recipes table. The pivot table formed shows user/recipe combinations to show which users have "hearted" which recipes.
    @ManyToMany(mappedBy = "heartedRecipes")
    @JsonIgnore
    private List<User> heartedUsers;


    // Method to insert current date on insert into database
    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }

    @Transient
    private boolean hearted;  // Not for table. Used for boolean 'hearted' check on html

    // ------------------------------------------
    // Constructors
    // ------------------------------------------

    public Recipe() {
    }

    public Recipe(String title, String description, int cookTime, int prepTime, int servings, boolean privateRecipe, User user) {
        this.title = title;
        this.description = description;
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.servings = servings;
        this.privateRecipe = privateRecipe;
        this.user = user;
    }

    // Used for creating a variant recipe
    public Recipe(String title, String description, int cookTime, int prepTime, int servings, boolean privateRecipe, User user, Recipe parentRecipe) {
        this.title = title;
        this.description = description;
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.servings = servings;
        this.privateRecipe = privateRecipe;
        this.user = user;
        this.parentRecipe = parentRecipe;
    }

    public Recipe(Recipe copy) {
        this.title = copy.title;
        this.description = copy.description;
        this.cookTime = copy.cookTime;
        this.prepTime = copy.prepTime;
        this.servings = copy.servings;
        this.dateCreated = copy.dateCreated;
        this.privateRecipe = copy.privateRecipe;
        this.user = copy.user;
        this.parentRecipe = copy.parentRecipe;
        this.recipeIngredients = copy.recipeIngredients;
        this.instructions = copy.instructions;
    }

    // ------------------------------------------
    // Getters / Setters
    // ------------------------------------------


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isPrivateRecipe() {
        return privateRecipe;
    }

    public void setPrivateRecipe(boolean privateRecipe) {
        this.privateRecipe = privateRecipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getParentRecipe() {
        return parentRecipe;
    }

    public void setParentRecipe(Recipe parentRecipe) {
        this.parentRecipe = parentRecipe;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public List<Stockpile> getStockpiles() {
        return stockpiles;
    }

    public void setStockpiles(List<Stockpile> stockpiles) {
        this.stockpiles = stockpiles;
    }

    public List<User> getHeartedUsers() {
        return heartedUsers;
    }

    public void setHeartedUsers(List<User> heartedUsers) {
        this.heartedUsers = heartedUsers;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl ) {
        this.imageUrl = imageUrl;
    }

    public boolean isHearted() {
        return hearted;
    }

    public void setHearted(boolean hearted) {
        this.hearted = hearted;
    }
}



// STRING THAT KEEPS URL THAT COMES BACK FROM FILESTACK