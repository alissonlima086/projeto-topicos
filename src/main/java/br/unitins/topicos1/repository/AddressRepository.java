package br.unitins.topicos1.repository;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.AddressResponseDTO;
import br.unitins.topicos1.model.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address>{
    public List<Address> findByCity(Long city){
        try{
            return find("city.id = ?1", city).list();
        } catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }
}