package rest.microservices.tasklistapiclone.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rest.microservices.tasklistapiclone.domain.exception.QuoteException;
import rest.microservices.tasklistapiclone.domain.exception.QuoteNotFoundException;
import rest.microservices.tasklistapiclone.domain.quote.Quote;
import rest.microservices.tasklistapiclone.repositories.QuoteRepository;
import rest.microservices.tasklistapiclone.services.QuoteService;
import rest.microservices.tasklistapiclone.util.MaxValue;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    private Logger logger = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private final MaxValue maxValue;

    @Override
    public Quote createQuote(Quote quote) {
        logger.info("Create quote: " + quote.toString());
        List<Quote> list = getAllQuote();
        if(list.size() > maxValue.getQuote()) throw new QuoteException("Превышено максимальное количесво цитат");
        return quoteRepository.save(quote);
    }

    @Override
    public Quote updateQuote(int id, Quote quote) {
        logger.info("Update quote with %d id".formatted(id));
        quote.setId(id);
        quoteRepository.save(quote);
        return quote;
    }

    @Override
    public Quote getQuote(int id) {
        logger.info("Get quote with %d id".formatted(id));
        return quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundException("Цитата с таким id не найдена"));
    }

    @Override
    public List<Quote> getAllQuote() {
        logger.info("Get all quotes");
        return quoteRepository.findAll();
    }

    @Override
    public void deleteQuote(int id) {
        logger.info("Delete quote with %d id".formatted(id));
        quoteRepository.deleteById(id);
    }
}
