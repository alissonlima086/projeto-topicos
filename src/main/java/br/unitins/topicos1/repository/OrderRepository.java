package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Order;
import br.unitins.topicos1.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findByUserWhereIsFinished (User user) {

        if (user == null)
            return null;

        return find("FROM Order WHERE user = ?1 AND ifFinished = true", user).list();
    }

    public Order findByUserWhereIsNotFinished (User user) {

        if (user == null)
            return null;

        return find("FROM Order WHERE usuario = ?1 AND ifFinished = false", user).firstResult();
    }

    public List<Order> findAll(String login) {
        return find("user.email = ?1", login).list();
    }
    
    public List<Order> findAll(Long idUser) {
        return find("user.id = ?1", idUser).list();
    }
}