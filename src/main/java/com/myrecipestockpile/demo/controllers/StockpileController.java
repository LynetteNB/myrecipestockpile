package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Stockpile;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.StockpileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StockpileController {

    private StockpileService stockpileService;
    private UsersRepository usersRepository;

    public StockpileController(StockpileService stockpileService, UsersRepository usersRepository) {
        this.stockpileService = stockpileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/stockpile/{id}")
    public String show(@PathVariable long id, Model vModel) {
        Stockpile stockpile = stockpileService.findOne(id);
        vModel.addAttribute("stockpile", stockpile);
        return "stockpile/show";
    }

    @GetMapping("/stockpile/new")
    public String showCreateStockpileForm(Model vModel) {
//        Stockpile stockpile = new Stockpile();
        vModel.addAttribute("stockpile", new Stockpile());
        return "stockpile/create";
    }

    //Uncomment the code in this method when authentication is implemented and delete the hardcoded user owner
    @PostMapping("/stockpile/create")
    public String createStockpile(@ModelAttribute Stockpile stockpile) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        stockpile.setUser(usersRepository.findOne(user.getId()));
        User user = usersRepository.findOne(1L);
        stockpile.setOwner(user);
        stockpileService.save(stockpile);
        return "redirect:/";
    }

    @GetMapping("/stockpile/{id}/edit")
    public String showEditStockpileForm(Model vModel, @PathVariable long id) {
        Stockpile stockpileToEdit = stockpileService.findOne(id);
        vModel.addAttribute("stockpile", stockpileToEdit);
        return "stockpile/edit";
    }

    @PostMapping("/stockpile/edit")
    public String updateStockpile(@ModelAttribute Stockpile stockpile) {
        stockpileService.save(stockpile);
        return "redirect:/";
    }

    @PostMapping("stockpile/{id}/delete")
    public String delete(@PathVariable long id) {
        stockpileService.delete(id);
        return "redirect:/";
    }

}
