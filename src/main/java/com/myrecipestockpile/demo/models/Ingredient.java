package com.myrecipestockpile.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String ingredient;

    // ------------------------------------------
    // Constructors
    // ------------------------------------------

    public Ingredient() {
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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
