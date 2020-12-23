package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.SectorRepository;
import com.onkar.stockMarket.model.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {

    @Autowired
    SectorRepository repo;

    public Sector addSector(Sector sector) {
        return repo.save(sector);
    }

    public List<Sector> findAllSector() {
        return repo.findAll();
    }

    public Sector findSectorById(long id) {
        return repo.findById(id).orElse(null);
    }

    public String deleteSector(long id) {
        repo.deleteById(id);
        return "Sector with id " + id + " deleted successfully";
    }

    public Sector updateSector(Sector sector) {
        Sector existingSector = this.findSectorById(sector.getSectorId());
        existingSector.setSectorName(sector.getSectorName());
        existingSector.setSectorDesc(sector.getSectorDesc());
        return repo.save(existingSector);
    }
}
