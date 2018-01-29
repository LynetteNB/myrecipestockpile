package com.myrecipestockpile.demo.models;

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

    // Connects to users table, it represents it's owner.
    @ManyToOne
    private User user;

    // Connects to another recipe's id, it represents the recipe which this one is a variation of. If null, it has no parent recipe and is not a variation.
    @OneToOne
    private Recipe parentRecipe;

    // Connects to instructions table, to show which instructions belong to it. Instructions sorted by primary key will show proper order.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Instruction> instructions;


    // Connects to stockpile table. The pivot table formed shows recipe/stockpile
    @ManyToMany(mappedBy = "stockpileRecipes")
    private List<Stockpile> stockpiles;

    // Connects to recipes table. The pivot table formed shows user/recipe combinations to show which users have "hearted" which recipes.
    @ManyToMany(mappedBy = "heartedRecipes")
    private List<User> heartedUsers;


    // Method to insert current date on insert into database
    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }

    // ------------------------------------------
    // Constructors
    // ------------------------------------------

    public Recipe() {
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
}