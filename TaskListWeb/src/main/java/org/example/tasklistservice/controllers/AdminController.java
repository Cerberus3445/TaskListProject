package org.example.tasklistservice.controllers;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.client.QuoteRestClient;
import org.example.tasklistservice.domain.quote.Quote;
import org.example.tasklistservice.exception.ErrorHandling;
import org.example.tasklistservice.exception.QuoteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/main")
@RequiredArgsConstructor
public class AdminController {

    private final QuoteRestClient quoteRestClient;

    private final ErrorHandling errorHandling;

    @GetMapping
    public String adminPage(){
        return "admin/adminPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model){
        model.addAttribute("quote", new Quote());
        return "admin/create";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable("id") int id, Model model){
        model.addAttribute("quote", quoteRestClient.getQuoteById(id));
        return "admin/update";
    }

    @GetMapping("/{id}")
    public String getCreatePage(@PathVariable("id") int id, Model model){
        model.addAttribute("quote", quoteRestClient.getQuoteById(id));
        return "admin/quote";
    }

    @PostMapping("/{id}/update")
    public String updatePage(@PathVariable("id") int id, Quote quote, Model model){
        try {
            quoteRestClient.updateQuote(id, quote);
        } catch (QuoteException quoteException){
            model.addAttribute("errors", errorHandling.handleQuoteException(quoteException));
            return "admin/update";
        }
        return "redirect:/admin/main";
    }

    @GetMapping("/list")
    public String quotesList(Model model){
        model.addAttribute("quotesList", quoteRestClient.getQuotes());
        return "admin/quotesList";
    }

    @DeleteMapping("/{id}")
    public String deletePage(@PathVariable("id") int id, Model model){
        try {
            quoteRestClient.deleteQuote(id);
        } catch (QuoteException quoteException){
            model.addAttribute("errors", errorHandling.handleQuoteException(quoteException));
            return "admin/quote";
        }
        return "redirect:/admin/main";
    }

    @PostMapping("/create")
    public String createQuote(Quote quote, Model model){
        try {
            quoteRestClient.createQuote(quote);
        } catch (QuoteException quoteException){
            model.addAttribute("errors", errorHandling.handleQuoteException(quoteException));
            return "admin/create";
        }
        return "redirect:/admin/main";
    }
}
