package com.onkar.stockMarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private Long companyId;
    private Double turnover;
    private String companyName;
    private String companyDesc;
    private String ceo;
    private String boardOfDirectors;
    private String sectorName;
}
