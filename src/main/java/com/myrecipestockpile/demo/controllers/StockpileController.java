package com.myrecipestockpile.demo.controllers;

import com.myrecipestockpile.demo.models.Stockpile;
import com.myrecipestockpile.demo.models.User;
import com.myrecipestockpile.demo.repositories.UsersRepository;
import com.myrecipestockpile.demo.services.StockpileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // THIS IS NOT WORKING
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User owner = usersRepository.findOne(currentUser.getId());
        stockpile.setOwner(currentUser);
        stockpileService.save(stockpile);
        return "redirect:/profile/" + stockpile.getOwner().getUsername();
    }

    @GetMapping("/stockpile/{id}/edit")
    public String showEditStockpileForm(Model vModel, @PathVariable long id) {
        Stockpile stockpileToEdit = stockpileService.findOne(id);
        vModel.addAttribute("stockpile", stockpileToEdit);
        return "stockpile/edit";
    }

    @PostMapping("/stockpile/edit")
    public String updateStockpile(@ModelAttribute Stockpile stockpile) {

        // Stockpile is loosing its owner when form is submitted. This is Hotfix.
        User owner = stockpileService.findOne(stockpile.getId()).getOwner();
        stockpile.setOwner(owner);

        stockpileService.save(stockpile);
        String url = "redirect:/stockpile/" + stockpile.getId();
        System.out.println(url);
        return url;
    }

    @PostMapping("stockpile/{id}/delete")
    public String delete(@PathVariable long id) {
        stockpileService.delete(id);
        return "redirect:/";
    }

}
