package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.GenreDTO;
import br.unitins.topicos1.dto.GenreResponseDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.dto.GenreResponseDTO;
import br.unitins.topicos1.model.Genre;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.GenreRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GenreServiceImpl implements GenreService{

    @Inject
    GenreRepository repository;

    @Override
    @Transactional
    public GenreResponseDTO insert(GenreDTO dto) {
        Genre genre = new Genre();

        genre.setName(dto.name());

        repository.persist(genre);

        return GenreResponseDTO.valueOf(genre);
    }

    @Override
    @Transactional
    public GenreResponseDTO update(Long id, GenreDTO dto) {
        Genre genre = repository.findById(id);

        genre.setName(dto.name());

        return GenreResponseDTO.valueOf(genre);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    public GenreResponseDTO findById(Long id) {
        return GenreResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<GenreResponseDTO> findByName(String name) {
        return repository.findByName(name).stream().map(e -> GenreResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<GenreResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> GenreResponseDTO.valueOf(e)).toList();
    }
    
}
