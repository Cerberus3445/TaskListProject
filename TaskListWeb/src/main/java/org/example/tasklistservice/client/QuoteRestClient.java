package org.example.tasklistservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.quote.Quote;
import org.example.tasklistservice.dto.QuoteDto;
import org.example.tasklistservice.exception.UserException;
import org.example.tasklistservice.proxy.FeignProxy;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class QuoteRestClient {

    private final ModelMapper modelMapper;

    private final FeignProxy feignProxy;

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public Quote getQuoteById(int id){
        try {
            QuoteDto quoteDto = feignProxy.getQuote(id);
            return modelMapper.map(quoteDto, Quote.class);
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new UserException(notFound.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void createQuote(Quote quote){
        try {
            QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
            feignProxy.createQuote(quoteDto);
        } catch (HttpClientErrorException.BadRequest badRequest){
            //TODO
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void deleteQuote(int id){
        try {
            feignProxy.deleteQuote(id);
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new UserException(notFound.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void updateQuote(int id, Quote quote){
        try {
            QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
            feignProxy.updateQuote(id, quoteDto);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new UserException(badRequest.getMessage());
        }
    }

    public Quote getRandomQuotes(){
        Random random = new Random();
        List<Quote> quoteList = getQuotes();
        return quoteList.get(random.nextInt(quoteList.size()));
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Quote> getQuotes(){
        try {
            List<QuoteDto> quoteDtoList = feignProxy.getQuoteList();
            List<Quote> quoteList = new ArrayList<>();
            for(QuoteDto quoteDto : quoteDtoList){
                quoteList.add(modelMapper.map(quoteDto, Quote.class));
            }
            return quoteList;
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new UserException(notFound.getMessage());
        }
    }
}
