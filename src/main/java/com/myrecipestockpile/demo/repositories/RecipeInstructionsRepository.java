package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Instruction;
import com.myrecipestockpile.demo.models.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeInstructionsRepository extends CrudRepository<Instruction, Long> {
    public List<Instruction> findByRecipe( Recipe recipe);
}
