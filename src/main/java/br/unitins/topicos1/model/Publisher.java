package br.unitins.topicos1.model;

import jakarta.persistence.Entity;

@Entity
public class Publisher extends DefaultEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
