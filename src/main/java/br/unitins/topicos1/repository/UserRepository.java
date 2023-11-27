package br.unitins.topicos1.repository;

import java.util.List;


import br.unitins.topicos1.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User>{
    public List<User> findByUsername(String username) {
        return find("UPPER(username) LIKE UPPER(?1) ", "%"+username+"%").list();
    }

    public User findByEmail(String email){
        try{
            return find("email = ?1", email).singleResult();
        } catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    public User findByEmailAndPassword(String email, String password) {
        try {
            return find("email = ?1 AND password = ?2 ", email, password).singleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
