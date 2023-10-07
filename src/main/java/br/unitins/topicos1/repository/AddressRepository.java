package br.unitins.topicos1.repository;

import java.util.List;

import org.jboss.logging.annotations.Param;
import org.jboss.resteasy.annotations.Query;

import br.unitins.topicos1.model.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address>{
    
}