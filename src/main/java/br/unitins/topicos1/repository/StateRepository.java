package br.unitins.topicos1.repository;

import br.unitins.topicos1.model.State;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StateRepository implements PanacheRepository<State>{
    
}
