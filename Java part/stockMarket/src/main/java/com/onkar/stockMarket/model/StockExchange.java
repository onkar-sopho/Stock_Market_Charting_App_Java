package com.onkar.stockMarket.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class StockExchange {
    @Id
    @GeneratedValue
    @Column(name = "stock_exchange_id")
    private Long stockExchangeId;

    @Column(name = "stock_exchange_name")
    private String stockExchangeName;

    @Column(name = "stock_exchange_desc")
    private String stockExchangeDesc;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "stock_exchange_remarks")
    private String stockExchangeRemarks;

    @ManyToMany(
            mappedBy = "stockExchangeList",
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    private List<Company> companies = new ArrayList<>();

    // Making sure that both sides of the relationship are populated
    public void setCompanies(Company company) {
        companies.add(company);
        company.getStockExchangeList().add(this);
    }
}
