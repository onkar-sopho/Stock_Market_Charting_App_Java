package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.StockPriceRepository;
import com.onkar.stockMarket.model.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockPriceService {

    @Autowired
    StockPriceRepository repo;

    public StockPrice addStock(StockPrice stockPrice) {
        return repo.save(stockPrice);
    }

    public List<StockPrice> findAllStock() {
        return repo.findAll();
    }

    public StockPrice findStockById(long id) {
        return repo.findById(id).orElse(null);
    }

    public String deleteStock(long id) {
        repo.deleteById(id);
        return "Stock with id " + id + " deleted successfully";
    }

    public StockPrice updateStock(StockPrice stockPrice) {
        StockPrice existingStock = this.findStockById(stockPrice.getStockId());
        existingStock.setCurrentPrice(stockPrice.getCurrentPrice());
        existingStock.setDate(stockPrice.getDate());
        existingStock.setTime(stockPrice.getTime());
        existingStock.setCompany(stockPrice.getCompany());
        existingStock.setStockExchange(stockPrice.getStockExchange());
        return repo.save(existingStock);
    }

}
