package org.example.tasklistservice.client;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.quote.Quote;
import org.example.tasklistservice.dto.QuoteDto;
import org.example.tasklistservice.exception.QuoteException;
import org.example.tasklistservice.proxy.FeignProxy;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class QuoteRestClient {

    private final ModelMapper modelMapper;

    private final FeignProxy feignProxy;

    private final Logger logger = LoggerFactory.getLogger(QuoteRestClient.class);

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public Quote getQuoteById(int id){
        logger.info("Get quote {}", id);
        try {
            QuoteDto quoteDto = feignProxy.getQuote(id);
            return modelMapper.map(quoteDto, Quote.class);
        } catch (FeignException e){
            throw new QuoteException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void createQuote(Quote quote){
        logger.info("Create quote {}", quote);
        try {
            QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
            feignProxy.createQuote(quoteDto);
        } catch (FeignException e){
            throw new QuoteException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void deleteQuote(int id){
        logger.info("Delete quote {}", id);
        try {
            feignProxy.deleteQuote(id);
        } catch (FeignException e){
            throw new QuoteException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void updateQuote(int id, Quote quote){
        logger.info("Update quote with id {}, {}", id, quote);
        try {
            QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
            feignProxy.updateQuote(id, quoteDto);
        } catch (FeignException e){
            throw new QuoteException(e.getMessage());
        }
    }

    public Quote getRandomQuotes(){
        logger.info("GetRandomQuotes");
        Random random = new Random();
        List<Quote> quoteList = getQuotes();
        return quoteList.get(random.nextInt(quoteList.size()));
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Quote> getQuotes(){
        logger.info("Get quotes");
        try {
            List<QuoteDto> quoteDtoList = feignProxy.getQuoteList();
            List<Quote> quoteList = new ArrayList<>();
            for(QuoteDto quoteDto : quoteDtoList){
                quoteList.add(modelMapper.map(quoteDto, Quote.class));
            }
            return quoteList;
        } catch (FeignException e){
            throw new QuoteException(e.getMessage());
        }
    }
}
