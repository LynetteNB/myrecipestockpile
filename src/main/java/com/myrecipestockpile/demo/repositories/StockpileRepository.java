package com.myrecipestockpile.demo.repositories;

import com.myrecipestockpile.demo.models.Stockpile;
import org.springframework.data.repository.CrudRepository;

public interface StockpileRepository extends CrudRepository<Stockpile, Long> {

}
