package com.in28minutes.rest.microservices.conversionservice.controllers;

import com.in28minutes.rest.microservices.conversionservice.models.CurrencyConversion;
import com.in28minutes.rest.microservices.conversionservice.proxy.CurrencyExchangeProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyExchangeProxy proxy;

    private final RestTemplate restTemplate;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("quantity") BigDecimal quantity
    ){
        ResponseEntity<CurrencyConversion> entity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/%s/to/%s".formatted(from, to),
                CurrencyConversion.class);
        CurrencyConversion currencyConversion = entity.getBody();

        return new CurrencyConversion(1000L, from, to, quantity, currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()) , currencyConversion.getEnvironment());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("quantity") BigDecimal quantity
    ){

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(1000L, from, to, quantity, currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()) , currencyConversion.getEnvironment() + " Feign");
    }
}
