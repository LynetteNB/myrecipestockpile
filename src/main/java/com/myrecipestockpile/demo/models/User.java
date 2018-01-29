package com.myrecipestockpile.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Connects to recipes table, to show which user owns a recipe.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Recipe> recipes;

    // Connects to stockpiles table, to show which user owns a stockpile.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Stockpile> stockpiles;

    // Connects to recipes table. The pivot table formed shows user/recipe combinations to show which users have "hearted" which recipes.
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "hearted_recipes",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")}
    )
    private List<Recipe> heartedRecipes;

    // ------------------------------------------
    // Constructors
    // ------------------------------------------

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Stockpile> getStockpiles() {
        return stockpiles;
    }

    public void setStockpiles(List<Stockpile> stockpiles) {
        this.stockpiles = stockpiles;
    }

    public List<Recipe> getHeartedRecipes() {
        return heartedRecipes;
    }

    public void setHeartedRecipes(List<Recipe> heartedRecipes) {
        this.heartedRecipes = heartedRecipes;
    }
}
