package br.unitins.topicos1.model;

import java.time.LocalDateTime;
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

    private LocalDateTime dateHour;
    
    private double totalOrder;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_order")
    private List<ItemOrder> itens;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address adress;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_payment", unique = true)
    private Payment payment;

    private Boolean ifFinished = false;

    public double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public List<ItemOrder> getItens() {
        return itens;
    }

    public void setItens(List<ItemOrder> itens) {
        this.itens = itens;
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

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public Boolean getIfFinished() {
        return ifFinished;
    }

    public void setIfFinished(Boolean ifFinished) {
        this.ifFinished = ifFinished;
    }
}
