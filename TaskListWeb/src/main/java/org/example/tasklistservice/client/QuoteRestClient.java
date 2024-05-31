package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.quote.Quote;
import org.example.tasklistservice.dto.QuoteDto;
import org.example.tasklistservice.exception.QuoteNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class QuoteRestClient {

    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    public Quote getQuoteById(int id){
        try {
            String url = "http://localhost:9002/api/quotes/%d".formatted(id);
            QuoteDto quoteDto = restTemplate.getForObject(url, QuoteDto.class);
            return modelMapper.map(quoteDto, Quote.class);
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new QuoteNotFoundException(notFound.getMessage());
        }
    }

    public Quote getRandomQuotes(){
        Random random = new Random();
        List<Quote> quoteList = getQuotes();
        return quoteList.get(random.nextInt(quoteList.size()));
    }

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
            throw new QuoteNotFoundException(notFound.getMessage());
        }
    }

    private HttpEntity<Objects> returnHttpHeadersWithBasicAuthForGetRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
        HttpEntity<Objects> http = new HttpEntity<>(headers);
        return http;
    }
}
