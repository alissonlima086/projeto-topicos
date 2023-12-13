package br.unitins.topicos1.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.topicos1.model.PhysicalPerson;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PhysicalPersonRepository implements PanacheRepository<PhysicalPerson> {

    public List<PhysicalPerson> findByName(String name) {
        if (name == null)
            return null;
        return find("UPPER(name) LIKE ?1 ", "%" + name.toUpperCase() + "%").list();
    }

    public boolean existsByCpf(String cpf){
        return find("cpf", cpf).count() > 0;
    }

}
