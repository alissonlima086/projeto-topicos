package br.unitins.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemOrder extends DefaultEntity {
    
    private int quantity;
    private double totalItem;
    private boolean idBuyed = false;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Comic product;

    @ManyToOne
    @JoinColumn(name = "id_Order")
    private Order order;

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalItem() {
        return totalItem;
    }
    public void setTotalItem(double totalItem) {
        this.totalItem = totalItem;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Comic getProduct() {
        return product;
    }
    public void setProduct(Comic product) {
        this.product = product;
    }
    public boolean isIdBuyed() {
        return idBuyed;
    }
    public void setIdBuyed(boolean idBuyed) {
        this.idBuyed = idBuyed;
    }
    
}
