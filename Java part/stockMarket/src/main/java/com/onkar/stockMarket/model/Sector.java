package com.onkar.stockMarket.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sector {
    @Id
    @GeneratedValue
    @Column(name = "sector_id")
    private Long sectorId;

    @Column(name = "sector_name")
    private String sectorName;

    @Column(name = "sector_desc")
    private String sectorDesc;

}
