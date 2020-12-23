package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.model.StockExchange;
import com.onkar.stockMarket.service.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockExchange")
public class StockExchangeController {
    @Autowired
    StockExchangeService service;

    @PostMapping("/add")
    public StockExchange addStockExchange(@RequestBody StockExchange stockExchange) {
        return service.addStockExchange(stockExchange);
    }

    @GetMapping("/all")
    public List<StockExchange> findAllStockExchange() {
        return service.findAllStockExchange();
    }

    @GetMapping("/find/{id}")
    public StockExchange findStockExchangeById(@PathVariable Long id) {
        return service.findStockExchangeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStockExchange(@PathVariable Long id) {
        return service.deleteStockExchange(id);
    }

    @PutMapping("/update")
    public StockExchange updateStockExchange(@RequestBody StockExchange stockExchange) {
        return service.updateStockExchange(stockExchange);
    }
}
