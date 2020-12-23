package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.CompanyRepository;
import com.onkar.stockMarket.dao.SectorRepository;
import com.onkar.stockMarket.dto.CompanyDto;
import com.onkar.stockMarket.exceptions.CompanyNotFoundException;
import com.onkar.stockMarket.exceptions.SectorNotFoundException;
import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.model.Sector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SectorRepository sectorRepository;

    private Company mapCompanyDto(CompanyDto companyDto) {
        Sector sector = sectorRepository.findBySectorName(companyDto.getSectorName())
                .orElseThrow(() -> new SectorNotFoundException(companyDto.getSectorName()));
        return Company.builder().companyName(companyDto.getCompanyName())
                .companyDesc(companyDto.getCompanyDesc())
                .ceo(companyDto.getCeo())
                .boardOfDirectors(companyDto.getBoardOfDirectors())
                .turnover(companyDto.getTurnover())
                .sector(sector)
                .build();
    }

    private CompanyDto mapCompany(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyId(company.getCompanyId());
        companyDto.setCompanyName(company.getCompanyName());
        companyDto.setCompanyDesc(company.getCompanyDesc());
        companyDto.setCeo(company.getCeo());
        companyDto.setBoardOfDirectors(company.getBoardOfDirectors());
        companyDto.setTurnover(company.getTurnover());
        companyDto.setSectorName(company.getSector().getSectorName());
        return companyDto;
    }

    public CompanyDto addCompany(CompanyDto companyDto) {
        Company company = companyRepository.save(mapCompanyDto(companyDto));
        companyDto.setCompanyId(company.getCompanyId());
        return companyDto;
    }

    public List<CompanyDto> findAllCompany() {
        List<CompanyDto> companyDtoList = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();
        for(Company company : companies) {
            companyDtoList.add(mapCompany(company));
        }
        return companyDtoList;
    }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id.toString()));
        existingCompany.setCompanyName(companyDto.getCompanyName());
        existingCompany.setCompanyDesc(companyDto.getCompanyDesc());
        existingCompany.setCeo(companyDto.getCeo());
        existingCompany.setBoardOfDirectors(companyDto.getBoardOfDirectors());
        //existingCompany.setStockExchangeList(companyDto.getStockExchangeList());
        existingCompany.setTurnover(companyDto.getTurnover());

        Sector sector = sectorRepository.findBySectorName(companyDto.getSectorName())
                .orElseThrow(() -> new SectorNotFoundException(companyDto.getSectorName()));
        existingCompany.setSector(sector);

        return mapCompany(companyRepository.save(existingCompany));
    }


//    public CompanyDto findCompanyById(Long id) {
//        Company company = companyRepository.findById(id)
//                .orElseThrow(() -> new CompanyNotFoundException(id.toString()));
//        return mapCompany(company);
//    }

    public CompanyDto findCompanyByName(String name) {
        Company company = companyRepository.findByCompanyName(name)
                .orElseThrow(() -> new CompanyNotFoundException(name));
        return mapCompany(company);
    }

    public String deleteCompany(long id) { companyRepository.deleteById(id);
        return "Company with id " + id + " deleted successfully";
    }
}
