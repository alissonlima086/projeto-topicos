package br.unitins.topicos1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class State extends DefaultEntity {

    @Column(length = 60)
    private String name;

    @Column(length = 2)
    private String abbreviation;

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getabbreviation() {
        return abbreviation;
    }

    public void setabbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}