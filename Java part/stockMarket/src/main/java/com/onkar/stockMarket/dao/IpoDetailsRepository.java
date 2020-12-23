package com.onkar.stockMarket.dao;

import com.onkar.stockMarket.model.IpoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpoDetailsRepository extends JpaRepository<IpoDetails, Long> {

}
