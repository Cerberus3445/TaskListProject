package rest.microservices.tasklistapiclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.microservices.tasklistapiclone.domain.quote.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

}
