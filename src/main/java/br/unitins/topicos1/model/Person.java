package br.unitins.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends DefaultEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
