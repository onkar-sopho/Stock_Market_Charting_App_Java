package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.IpoDetailsRepository;
import com.onkar.stockMarket.model.IpoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpoDetailsService {

    @Autowired
    IpoDetailsRepository repo;

    public IpoDetails addIpoDetails(IpoDetails ipoDetails) {
        return repo.save(ipoDetails);
    }

    public List<IpoDetails> findAllIpoDetails() {
        return repo.findAll();
    }

    public IpoDetails findIpoDetailsById(long id) {
        return repo.findById(id).orElse(null);
    }

    public String deleteIpoDetails(long id) {
        repo.deleteById(id);
        return "IPO with id " + id + " deleted successfully";
    }

    public IpoDetails updateIpoDetails(IpoDetails ipoDetails) {
        IpoDetails existingIpoDetails = this.findIpoDetailsById(ipoDetails.getIpoId());
        existingIpoDetails.setCompany(ipoDetails.getCompany());
        existingIpoDetails.setIpoRemarks(ipoDetails.getIpoRemarks());
        existingIpoDetails.setClosingDate(ipoDetails.getClosingDate());
        existingIpoDetails.setOpeningDate(ipoDetails.getOpeningDate());
        existingIpoDetails.setNumberOfShares(ipoDetails.getNumberOfShares());
        existingIpoDetails.setPricePerShare(ipoDetails.getPricePerShare());
        existingIpoDetails.setStockExchange(ipoDetails.getStockExchange());
        return repo.save(existingIpoDetails);
    }
}
