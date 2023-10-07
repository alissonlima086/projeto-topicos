package br.unitins.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comic extends Product {
    private Integer numPages;

    private Binding binding;

    @ManyToOne
    @JoinColumn(name = "id_publisher")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
