package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.model.IpoDetails;
import com.onkar.stockMarket.service.IpoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ipo")
public class IpoDetailsController {

    @Autowired
    IpoDetailsService service;

    @PutMapping("/add")
    public IpoDetails addIpoDetails(@RequestBody IpoDetails ipoDetails) {
        return service.addIpoDetails(ipoDetails);
    }

    @GetMapping("/all")
    public List<IpoDetails> findAllIpoDetails() {
        return service.findAllIpoDetails();
    }

    @GetMapping("/find/{id}")
    public IpoDetails findIpoDetailsById(@PathVariable Long id) {
        return service.findIpoDetailsById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteIpoDetails(@PathVariable Long id) {
        return service.deleteIpoDetails(id);
    }

    @PutMapping("/update")
    public IpoDetails updateIpoDetails(@RequestBody IpoDetails ipoDetails) {
        return service.updateIpoDetails(ipoDetails);
    }
}
