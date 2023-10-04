package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User>{
    public List<User> findByUsername(String username) {
        return find("UPPER(username) LIKE UPPER(?1) ", "%"+username+"%").list();
    }
}
