package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Instruction;
import org.springframework.data.repository.CrudRepository;

public interface RecipeInstructionsRepository extends CrudRepository<Instruction, Long> {
}
