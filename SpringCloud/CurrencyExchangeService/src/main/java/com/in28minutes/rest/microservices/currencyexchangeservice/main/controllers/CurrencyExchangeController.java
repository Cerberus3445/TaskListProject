package com.in28minutes.rest.microservices.currencyexchangeservice.main.controllers;

import com.in28minutes.rest.microservices.currencyexchangeservice.main.models.CurrencyExchange;
import com.in28minutes.rest.microservices.currencyexchangeservice.main.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final CurrencyExchangeRepository currencyExchangeRepository;

    private final Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ){
        //2024-06-21T17:28:10.302+10:00  INFO [currency-exchange,d4e6b9f1654add6e132682701b2c0dcd,99962878ea3ffedf] #SB3 10744
        //d4e6b9f1654add6e132682701b2c0dcd - id запроса(micrometer присваивает)

        logger.info("retrieveExchangeValue from {} to {}", from, to);
//        return new CurrencyExchange(100L, from, to, BigDecimal.valueOf(50), environment.getProperty("local.server.port"));

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

        if(currencyExchange == null){
            throw new RuntimeException("Таких данных нет");
        }

        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
