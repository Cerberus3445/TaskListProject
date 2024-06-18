package rest.microservices.tasklistapiclone.services;


import rest.microservices.tasklistapiclone.domain.quote.Quote;

import java.util.List;

public interface QuoteService {

    Quote createQuote(Quote quote);

    Quote updateQuote(int id, Quote quote);

    Quote getQuote(int id);

    List<Quote> getAllQuote();

    void deleteQuote(int id);
}
