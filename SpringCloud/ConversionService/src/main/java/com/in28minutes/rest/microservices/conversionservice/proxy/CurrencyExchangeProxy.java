package com.in28minutes.rest.microservices.conversionservice.proxy;

import com.in28minutes.rest.microservices.conversionservice.models.CurrencyConversion;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000") //spring.application.name=currency-exchange берем имя из микросервиса
@FeignClient(name = "currency-exchange") //Для того, чтобы FeignClient связался с Eureka и распределил нагрузку между сервисами, нужно убрать поле url
public interface CurrencyExchangeProxy {
    
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );
}
