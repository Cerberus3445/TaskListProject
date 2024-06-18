package org.example.tasklist.repositories;

import org.example.tasklist.domain.quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

}
