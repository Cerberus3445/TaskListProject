package com.in28minutes.rest.microservices.currencyexchangeservice.main.repository;
import com.in28minutes.rest.microservices.currencyexchangeservice.main.models.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    CurrencyExchange findByFromAndTo(String from, String to);
}
