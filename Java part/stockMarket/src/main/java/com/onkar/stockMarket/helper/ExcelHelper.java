package com.onkar.stockMarket.helper;

import com.onkar.stockMarket.dao.CompanyRepository;
import com.onkar.stockMarket.dao.StockExchangeRepository;
import com.onkar.stockMarket.dto.ExcelSummary;
import com.onkar.stockMarket.exceptions.CompanyNotFoundException;
import com.onkar.stockMarket.exceptions.StockExchangeNotFoundException;
import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.model.StockExchange;
import com.onkar.stockMarket.model.StockPrice;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class ExcelHelper {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @Autowired
    ExcelSummary excelSummary;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Company Code", "Stock Exchange", "Price Per Share(in Rs)", "Date", "Time"};
    static String SHEET = "Sheet1";
    public static Long compId = 0l;
    public static Long stockExId = 0l;
    public static Long records = 0l;

    public boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<StockPrice> excelToStockPrices(InputStream is) {
        DataFormatter dataFormatter = new DataFormatter();
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<StockPrice> stockPrices = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                records++;
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                StockPrice stockPrice = new StockPrice();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            Long id = (long) currentCell.getNumericCellValue();
                            compId = id;
                            Company company = companyRepository.findById(id)
                                    .orElseThrow(() -> new CompanyNotFoundException(id.toString()));
                            stockPrice.setCompany(company);
                            break;

                        case 1:
                            Long idNew = (long) currentCell.getNumericCellValue();
                            stockExId = idNew;
                            StockExchange stockExchange = stockExchangeRepository.findById(idNew)
                                    .orElseThrow(() -> new StockExchangeNotFoundException(idNew.toString()));
                            stockPrice.setStockExchange(stockExchange);
                            break;

                        case 2:
                            stockPrice.setCurrentPrice(currentCell.getNumericCellValue());
                            break;

                        case 3:
                            stockPrice.setDate(dataFormatter.formatCellValue(currentCell));
                            break;

                        case 4:
                            stockPrice.setTime(dataFormatter.formatCellValue(currentCell));
                            break;

                        default:
                            break;
                    }
                    cellIdx++;
                }
                stockPrices.add(stockPrice);
            }
            workbook.close();

            return stockPrices;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

    public ExcelSummary getExcelSummary() {
        return this.excelSummary;
    }
}