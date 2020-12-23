package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.StockExchangeRepository;
import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.model.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockExchangeService {

    @Autowired
    StockExchangeRepository repo;

    public StockExchange addStockExchange(StockExchange stockExchange) {
        return repo.save(stockExchange);
    }

    public List<StockExchange> findAllStockExchange() {
        return repo.findAll();
    }

    public StockExchange findStockExchangeById(long id) {
        return repo.findById(id).orElse(null);
    }

    public String deleteStockExchange(long id) {
        repo.deleteById(id);
        return "Stock Exchange with id " + id + " deleted successfully";
    }

    public StockExchange updateStockExchange(StockExchange stockExchange) {
        StockExchange existingStockExchange = this.findStockExchangeById(stockExchange.getStockExchangeId());
        existingStockExchange.setStockExchangeName(stockExchange.getStockExchangeName());
        existingStockExchange.setStockExchangeDesc(stockExchange.getStockExchangeDesc());
        existingStockExchange.setContactAddress(stockExchange.getContactAddress());
        existingStockExchange.setStockExchangeRemarks(stockExchange.getStockExchangeRemarks());

        // Updating compaines present inside the particular stock exchange
        for(Company company : stockExchange.getCompanies()) {
            existingStockExchange.setCompanies(company);
        }
        return repo.save(existingStockExchange);
    }
}
