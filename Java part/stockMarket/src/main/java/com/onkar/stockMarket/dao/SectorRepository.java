package com.onkar.stockMarket.dao;

import com.onkar.stockMarket.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>{
    Optional<Sector> findBySectorName(String sectorName);
}
