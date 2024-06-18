package org.example.tasklist.services;

import org.example.tasklist.domain.quote.Quote;

import java.util.List;

public interface QuoteService {

    Quote createQuote(Quote quote);

    Quote updateQuote(int id, Quote quote);

    Quote getQuote(int id);

    List<Quote> getAllQuote();

    void deleteQuote(int id);
}
