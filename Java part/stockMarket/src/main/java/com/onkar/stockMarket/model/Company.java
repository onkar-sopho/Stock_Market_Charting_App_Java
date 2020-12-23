package com.onkar.stockMarket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private long companyId;

    @Column(name = "turnover")
    private double turnover;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_desc")
    private String companyDesc;

    @Column(name = "ceo")
    private String ceo;

    @Column(name = "board_of_directors")
    private String boardOfDirectors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_sector_id")
    private Sector sector;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Company_Stock_Exchange",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "stock_exchange_id")}
    )
    private List<StockExchange> stockExchangeList = new ArrayList<>();

}
