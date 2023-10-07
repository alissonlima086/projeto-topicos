package br.unitins.topicos1.repository;

import br.unitins.topicos1.model.Phone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PhoneRepository implements PanacheRepository<Phone>{
    
}
