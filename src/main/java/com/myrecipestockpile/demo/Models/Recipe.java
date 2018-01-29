package com.myrecipestockpile.demo.Models;

import javax.persistence.*;
import java.util.Date;

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
}