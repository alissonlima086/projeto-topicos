package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Order;
import br.unitins.topicos1.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findOrderByIdUser(User user){
        if (user == null)
            return null;
        return find("FROM Order WHERE user = ?1",user).list();
}
}
