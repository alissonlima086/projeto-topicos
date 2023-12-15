package br.unitins.topicos1.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment extends DefaultEntity {

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Boolean confirmationPayment;

    private LocalDate dateConfimationPayment;

    public Payment(Double value) {

        this.value = value;
        this.confirmationPayment = false;
        this.dateConfimationPayment = LocalDate.now();
    }

    public Payment() {

        this.confirmationPayment = false;
    }

    public Boolean getConfirmationPayment() {
        return confirmationPayment;
    }

    public void setConfirmationPayment(Boolean confirmationPayment) {
        this.confirmationPayment = confirmationPayment;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDateConfimationPayment() {
        return dateConfimationPayment;
    }

    public void setDateConfimationPayment(LocalDate dateConfimationPayment) {
        this.dateConfimationPayment = dateConfimationPayment;
    }

}
