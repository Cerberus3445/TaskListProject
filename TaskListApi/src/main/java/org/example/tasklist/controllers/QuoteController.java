package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.QuoteException;
import org.example.tasklist.domain.quote.Quote;
import org.example.tasklist.dto.QuoteDto;
import org.example.tasklist.services.impl.QuoteServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/v1/tasklist-api/quotes")
@RequiredArgsConstructor
@Tag(name = "Quote Controller", description = "Quote API")
public class QuoteController {

    private final QuoteServiceImpl quoteService;

    private final ModelMapper modelMapper;

    private final Environment environment;

    @GetMapping
    public List<Quote> getQuoteList(){
        return quoteService.getAllQuote();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDto> getQuoteById(@PathVariable("id") int id){
        QuoteDto quoteDto = modelMapper.map(quoteService.getQuote(id), QuoteDto.class);
        quoteDto.setEnvironment(environment.getProperty("local.server.port"));
        return ResponseEntity.ok(quoteDto);
    }

    @PostMapping
    public ResponseEntity<QuoteDto> createQuote(@Valid @RequestBody QuoteDto quoteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new QuoteException(formatErrorsToString(bindingResult));
        }
        Quote quote = modelMapper.map(quoteDto, Quote.class);
        quoteService.createQuote(quote);
        quoteDto.setEnvironment(environment.getProperty("local.server.port"));
        return ResponseEntity.ok(quoteDto);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<QuoteDto> updateQuote(@PathVariable("id") int id, @Valid @RequestBody QuoteDto quoteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new QuoteException(formatErrorsToString(bindingResult));
        }
        Quote quote = modelMapper.map(quoteDto, Quote.class);
        quoteService.updateQuote(id, quote);
        quoteDto.setEnvironment(environment.getProperty("local.server.port"));
        return ResponseEntity.ok(quoteDto);
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
