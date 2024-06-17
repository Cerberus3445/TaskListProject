package org.example.tasklist.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.QuoteNotFoundException;
import org.example.tasklist.domain.quote.Quote;
import org.example.tasklist.repositories.QuoteRepository;
import org.example.tasklist.services.QuoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    @Override
    public Quote createQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    @Override
    public Quote updateQuote(int id, Quote quote) {
        quote.setId(id);
        quoteRepository.save(quote);
        return quote;
    }

    @Override
    public Quote getQuote(int id) {
        return quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundException("Quote with this id not found"));
    }

    @Override
    public List<Quote> getAllQuote() {
        return quoteRepository.findAll();
    }

    @Override
    public void deleteQuote(int id) {
        quoteRepository.deleteById(id);
    }
}
