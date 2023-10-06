package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.City;
import br.unitins.topicos1.model.Genre;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CityRepository implements PanacheRepository<City>{
    public List<City> findByName(String name) {
        return find("UPPER(name) LIKE UPPER(?1) ", "%"+name+"%").list();
    }
    
}
