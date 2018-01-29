package com.myrecipestockpile.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stockpiles")
public class Stockpile {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    // User's stockpiles are defaulted to public
    @Column(nullable = false)
    private boolean privateStockpile = false;

    // Connects to users table. It shows who is the owner of a stockpile.
    @ManyToOne
    private User owner;

    // Connects to recipes table. It shows which recipes belong to this stockpile.
    @ManyToMany
    @JoinTable(
            name = "stockpile_recipes",
            joinColumns = {@JoinColumn(name = "stockpile_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")}
    )
    private List<Recipe> stockpileRecipes;

    // -------------

    public Stockpile() {
    }


    // -------------


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivateStockpile() {
        return privateStockpile;
    }

    public void setPrivateStockpile(boolean privateStockpile) {
        this.privateStockpile = privateStockpile;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Recipe> getstockpileRecipes() {
        return stockpileRecipes;
    }

    public void setstockpileRecipes(List<Recipe> stockpileRecipes) {
        this.stockpileRecipes = stockpileRecipes;
    }
}
