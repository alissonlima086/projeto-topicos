package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AuthorDTO;
import br.unitins.topicos1.dto.AuthorResponseDTO;
import br.unitins.topicos1.model.Author;
import br.unitins.topicos1.repository.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AuthorServiceImpl implements AuthorService {

    @Inject
    AuthorRepository repository;

    @Override
    @Transactional
    public AuthorResponseDTO insert(AuthorDTO dto) {
        Author newAuthor = new Author();

        newAuthor.setName(dto.name());
        newAuthor.setEmail(dto.email());

        repository.persistAndFlush(newAuthor);

        return AuthorResponseDTO.valueOf(newAuthor);
    }

    @Override
    @Transactional
    public AuthorResponseDTO update(Long id, AuthorDTO dto) {
        Author Author = repository.findById(id);
        Author.setName(dto.name());
        Author.setEmail(dto.email());

        return AuthorResponseDTO.valueOf(Author);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    public AuthorResponseDTO findById(long id) {
        return AuthorResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<AuthorResponseDTO> findByName(String name) {
        return repository.findByName(name).stream().map(e -> AuthorResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AuthorResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> AuthorResponseDTO.valueOf(e)).toList();
    }
    
}
