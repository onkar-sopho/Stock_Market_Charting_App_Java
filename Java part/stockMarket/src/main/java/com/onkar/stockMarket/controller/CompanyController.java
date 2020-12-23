package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.dto.CompanyDto;
import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/add")
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.addCompany(companyDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyDto>> findAllCompany() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.findAllCompany());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(companyService.updateCompany(id, companyDto));
    }

//    @GetMapping("/find/{id}")
//    public ResponseEntity<CompanyDto> findCompanyById(@PathVariable Long id) {
//        return ResponseEntity.status(HttpStatus.OK)
//            .body(companyService.findCompanyById(id));
//    }

    @GetMapping("/find/{name}")
    public ResponseEntity<CompanyDto> findCompanyByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.findCompanyByName(name));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }
}
