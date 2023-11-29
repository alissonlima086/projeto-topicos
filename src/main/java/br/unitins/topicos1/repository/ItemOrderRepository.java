package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.model.ItemOrder;
import br.unitins.topicos1.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemOrderRepository implements PanacheRepository<ItemOrder> {
    
    public List<ItemOrder> findByProduto (Comic product) {

        if (product == null)
            return null;

        return find("FROM ItemOrder WHERE product = ?1", product).list();
    }

    public List<ItemOrder> findItemOrderByUser(User user) {

        if (user == null)
            return null;

            boolean item = false;

        return find("FROM ItemOrder WHERE user = ?1 AND idBuyed = ?2", user,item).list();
    }
}
