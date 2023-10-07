package br.unitins.topicos1.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "author_table")
public class Author extends DefaultEntity {
    private String authorName;
    private String email;

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Phone> phoneList;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public List<Phone> getPhoneList() {
    //     return phoneList;
    // }

    // public void setPhoneList(List<Phone> phoneList) {
    //     this.phoneList = phoneList;
    // }

}
