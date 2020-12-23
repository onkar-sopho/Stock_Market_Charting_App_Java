package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.model.StockPrice;
import com.onkar.stockMarket.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockPrice")
public class StockPriceController {

    @Autowired
    StockPriceService service;

    @PostMapping("/add")
    public StockPrice addStock(@RequestBody StockPrice stockPrice) {
        return service.addStock(stockPrice);
    }

    @GetMapping("/all")
    public List<StockPrice> findAllStock() {
        return service.findAllStock();
    }

    @GetMapping("/find/{id}")
    public StockPrice findStockById(@PathVariable Long id) {
        return service.findStockById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStock(@PathVariable Long id) {
        return service.deleteStock(id);
    }

    @PutMapping("/update")
    public StockPrice updateStock(@RequestBody StockPrice stockPrice) {
        return service.updateStock(stockPrice);
    }

}
