package br.unitins.topicos1.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_table")
public class Order extends DefaultEntity {
    
    private double totalOrder = 0;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_order")
    private List<ItemOrder> listOfItens;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address adress;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_payment", unique = true)
    private Payment payment;

    public double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public List<ItemOrder> getListOfItens() {
        return listOfItens;
    }

    public void setListOfItens(List<ItemOrder> listOfItens) {
        this.listOfItens = listOfItens;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    
}
