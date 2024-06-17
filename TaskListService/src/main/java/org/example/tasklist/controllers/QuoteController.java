package org.example.tasklist.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.QuoteException;
import org.example.tasklist.domain.quote.Quote;
import org.example.tasklist.dto.QuoteDto;
import org.example.tasklist.services.impl.QuoteServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteServiceImpl quoteService;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<Quote> getQuoteList(){
        return quoteService.getAllQuote();
    }

    @GetMapping("/{id}")
    public Quote getQuoteById(@PathVariable("id") int id){
        return quoteService.getQuote(id);
    }

    @PostMapping("/create")
    public Quote createQuote(@Valid @RequestBody QuoteDto quoteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new QuoteException(formatErrorsToString(bindingResult));
        }
        Quote quote = modelMapper.map(quoteDto, Quote.class);
        return quoteService.createQuote(quote);
    }

    @PostMapping("/{id}/update")
    public Quote updateQuote(@PathVariable("id") int id, @Valid @RequestBody QuoteDto quoteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new QuoteException(formatErrorsToString(bindingResult));
        }
        Quote quote = modelMapper.map(quoteDto, Quote.class);
        return quoteService.updateQuote(id, quote);
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteQuote(@PathVariable("id") int id){
        try {
            quoteService.deleteQuote(id);
            return HttpStatus.OK;
        } catch (HttpClientErrorException.BadRequest.NotFound notFound){
            throw new QuoteException("Quote with this id not found");
        }
    }

    private String formatErrorsToString(BindingResult bindingResult){
        StringBuilder stringBuilder = new StringBuilder();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            stringBuilder.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage());
        }
        return stringBuilder.toString();
    }
}
