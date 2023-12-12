package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findAll(String login) {
        return find("user.email = ?1", login).list();
    }
    
    public List<Order> findAll(Long idUser) {
        return find("user.id = ?1", idUser).list();
    }
}