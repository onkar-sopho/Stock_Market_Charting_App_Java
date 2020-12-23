package com.onkar.stockMarket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class IpoDetails {
    @Id
    @GeneratedValue
    @Column(name = "ipo_id")
    private Long ipoId;

    @Column(name = "no_of_shares")
    private Long numberOfShares;

    @Column(name = "price_per_share")
    private Double pricePerShare;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_stock_exchange_id")
    private StockExchange stockExchange;

    @Column(name = "ipo_remarks")
    private String ipoRemarks;

    @Column(name = "opening_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date openingDate;

    @Column(name = "closing_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date closingDate;
}
