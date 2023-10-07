package br.unitins.topicos1.repository;

import java.util.List;

import br.unitins.topicos1.model.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author>{
    public List<Author> findByName(String authorName) {
        return find("UPPER(Authorname) LIKE UPPER(?1) ", "%"+authorName+"%").list();
    }
}
