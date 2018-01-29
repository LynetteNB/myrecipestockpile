package com.myrecipestockpile.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id @GeneratedValue
    private long id;

    // Connects to recipe table, to show which recipe this recipe_ingredient belongs to.
    @ManyToOne
    private Recipe recipe;

    // Quantity will hold both number and measurement. example "1/2 Cup" or "40 oz."
    @Column(nullable = false)
    private String quantity;

    // Connects to ingredients table, to show what type of ingredient goes with the quantity.
    @ManyToOne
    private Ingredient ingredient;


    // ------------------------------------------
    // Constructors
    // ------------------------------------------

    public RecipeIngredient() {
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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
