package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.StockPriceRepository;
import com.onkar.stockMarket.helper.ExcelHelper;
import com.onkar.stockMarket.model.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    ExcelHelper excelHelper;

    @Autowired
    StockPriceRepository stockPriceRepository;

    public void save(MultipartFile file) {
        try {
            List<StockPrice> stockPriceList = excelHelper.excelToStockPrices(file.getInputStream());
            stockPriceRepository.saveAll(stockPriceList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store the excel data: " + e.getMessage());
        }
    }

}
