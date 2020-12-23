package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.model.Sector;
import com.onkar.stockMarket.service.CompanyService;
import com.onkar.stockMarket.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sector")
public class SectorController {
    @Autowired
    SectorService service;

    @PostMapping("/add")
    public Sector addSector(@RequestBody Sector sector) {
        return service.addSector(sector);
    }

    @GetMapping("/all")
    public List<Sector> findAllSector() {
        return service.findAllSector();
    }

    @GetMapping("/find/{id}")
    public Sector findSectorById(@PathVariable Long id) {
        return service.findSectorById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSector(@PathVariable Long id) {
        return service.deleteSector(id);
    }

    @PutMapping("/update")
    public Sector updateSector(@RequestBody Sector sector) {
        return service.updateSector(sector);
    }
}
