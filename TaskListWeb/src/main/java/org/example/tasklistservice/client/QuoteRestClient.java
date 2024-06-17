package org.example.tasklistservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.quote.Quote;
import org.example.tasklistservice.dto.QuoteDto;
import org.example.tasklistservice.exception.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@RequiredArgsConstructor
public class QuoteRestClient {

    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public Quote getQuoteById(int id){
        try {
            String url = "http://localhost:9002/api/quotes/%d".formatted(id);
            QuoteDto quoteDto = restTemplate.exchange(url,HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), QuoteDto.class).getBody();
            return modelMapper.map(quoteDto, Quote.class);
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new UserException(notFound.getMessage());
        }
    }

    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void createQuote(Quote quote){
        try {
            String url = "http://localhost:9002/api/quotes/create";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
            HttpEntity<Object> http = new HttpEntity<>(toJson(quoteDto), headers);
            restTemplate.exchange(url, HttpMethod.POST, http, QuoteDto.class).getBody();
        } catch (HttpClientErrorException.BadRequest badRequest){
            //TODO
        }
    }

    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void deleteQuote(int id){
        try {
            String url = "http://localhost:9002/api/quotes/%d/delete".formatted(id);
             restTemplate.exchange(url,HttpMethod.DELETE, returnHttpHeadersWithBasicAuthForGetRequest(), String.class);
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new UserException(notFound.getMessage());
        }
    }

    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public Quote updateQuote(int id, Quote quote){
        try {
            String url = "http://localhost:9002/api/quotes/%d/update".formatted(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
            HttpEntity<Object> http = new HttpEntity<>(toJson(quoteDto), headers);
           QuoteDto updatedQuoteDto =  restTemplate.exchange(url, HttpMethod.POST, http, QuoteDto.class).getBody();
           return modelMapper.map(updatedQuoteDto, Quote.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new UserException(badRequest.getMessage());
        }
    }

    public Quote getRandomQuotes(){
        Random random = new Random();
        List<Quote> quoteList = getQuotes();
        return quoteList.get(random.nextInt(quoteList.size()));
    }

    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Quote> getQuotes(){
        try {
            String url = "http://localhost:9002/api/quotes";
            QuoteDto[] quoteDtoArray = restTemplate.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), QuoteDto[].class).getBody();
            List<Quote> quoteList = new ArrayList<>();
            for(QuoteDto quoteDto : quoteDtoArray){
                quoteList.add(modelMapper.map(quoteDto, Quote.class));
            }
            return quoteList;
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new UserException(notFound.getMessage());
        }
    }

    private HttpEntity<Objects> returnHttpHeadersWithBasicAuthForGetRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
        return new HttpEntity<>(headers);
    }

    private Map<String, String> toJson(QuoteDto quoteDto){
        Map<String, String> map = new HashMap<>();
        map.put("text", quoteDto.getText());
        map.put("author", quoteDto.getAuthor());
        return map;
    }
}
