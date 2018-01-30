package com.myrecipestockpile.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "recipe_instructions")
public class Instruction {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String instruction;

    // Connects to recipes table, it represents the recipe id which this instruction belongs to.
    @ManyToOne
    private Recipe recipe;

    // ------------------------------------------
    // Constructors
    // ------------------------------------------

    public Instruction() {
    }

    public Instruction(String instruction, Recipe recipe) {
        this.recipe = recipe;
        this.instruction = instruction;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}