package br.unitins.topicos1.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Pix extends Payment {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    private LocalDate dateExpirationTokenPix;

    public Pix(Double value, String name, String cpf) {

        super(value);

        this.name = name;
        this.cpf = cpf;
        this.dateExpirationTokenPix = LocalDate.now().plusDays(1);
    }

    public Pix() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateExpirationTokenPix() {
        return dateExpirationTokenPix;
    }

    public void setDateExpirationTokenPix(LocalDate dateExpirationTokenPix) {
        this.dateExpirationTokenPix = dateExpirationTokenPix;
    }
}