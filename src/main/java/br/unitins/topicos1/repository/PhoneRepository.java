package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Phone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class PhoneRepository implements PanacheRepository<Phone>{
    public List<Phone> findPhoneByUserId(Long userId){
        try{
            return find("user.id = ?1", +userId).list();
        } catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }
}
