package br.unitins.topicos1.model;

import jakarta.persistence.Entity;

@Entity
public class Author extends Person {
    private String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
