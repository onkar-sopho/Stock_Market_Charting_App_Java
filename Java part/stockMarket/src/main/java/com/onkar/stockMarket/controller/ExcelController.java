package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.dao.CompanyRepository;
import com.onkar.stockMarket.dao.StockExchangeRepository;
import com.onkar.stockMarket.dto.ExcelSummary;
import com.onkar.stockMarket.exceptions.CompanyNotFoundException;
import com.onkar.stockMarket.exceptions.StockExchangeNotFoundException;
import com.onkar.stockMarket.helper.ExcelHelper;
import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.model.StockExchange;
import com.onkar.stockMarket.service.CompanyService;
import com.onkar.stockMarket.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import-data")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @Autowired
    ExcelHelper excelHelper;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        String message = "";
        if(excelHelper.hasExcelFormat(file)) {
            try {
                excelService.save(file);
                message = "File uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() +  "....Sorry";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(message);
            }
        }
        message = "Please upload an excel file first";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @GetMapping("/summary")
    public ResponseEntity<ExcelSummary> getSummary() {
        Company company = companyRepository.findById(ExcelHelper.compId)
                .orElseThrow(() -> new CompanyNotFoundException(ExcelHelper.compId.toString()));
        StockExchange stockExchange = stockExchangeRepository.findById(ExcelHelper.stockExId)
                .orElseThrow(() -> new StockExchangeNotFoundException(ExcelHelper.stockExId.toString()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ExcelSummary(excelHelper.records-2, company.getCompanyName(),
                        stockExchange.getStockExchangeName()));
    }

}
