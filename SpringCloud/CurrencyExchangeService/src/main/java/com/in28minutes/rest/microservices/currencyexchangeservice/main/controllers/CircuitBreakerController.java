package com.in28minutes.rest.microservices.currencyexchangeservice.main.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    //@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse") //пытаемся несколько раз, hardcodedResponse - метод
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse") //если микросервис после многочисленных попыток все-равно не работает, то возвращает ответ из fallbackMethod
    @RateLimiter(name = "sample-api") //к примеру за 10 секунд разрешить 10.000 вызовов
    @Bulkhead(name = "sample-api") //максимальное количество одновременных вызовов
    public String sampleApi(){
        logger.info("Sample API");
       //String string =  new RestTemplate().getForObject("http://localhost:9001/web/user/1/update", String.class);
        String string = "Sample API";
        return string;
    }

    private String hardcodedResponse(Exception ex){ //в параметрах должен быть класс, унаследованный от Throwable
        return "fallback-response";
    }
}
