package br.unitins.topicos1.repository;

import br.unitins.topicos1.model.CreditCard;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditCardRepository implements PanacheRepository<CreditCard> {
    
}
