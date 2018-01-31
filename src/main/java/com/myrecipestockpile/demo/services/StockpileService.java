package com.myrecipestockpile.demo.services;

import com.myrecipestockpile.demo.models.Stockpile;
import com.myrecipestockpile.demo.repositories.StockpileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockpileService {
    private StockpileRepository stockpileRepository;

    @Autowired
    public StockpileService(StockpileRepository stockpileRepository) {
        this.stockpileRepository = stockpileRepository;
    }

    public void save(Stockpile stockpile) {
        stockpileRepository.save(stockpile);
    }

    public Stockpile findOne(long id) {
        return stockpileRepository.findOne(id);
    }

    public void delete(long id) {
        stockpileRepository.delete(id);
    }

}
