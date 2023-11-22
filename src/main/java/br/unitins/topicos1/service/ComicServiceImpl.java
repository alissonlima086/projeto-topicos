package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;
import br.unitins.topicos1.model.Binding;
import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.repository.ComicRepository;
import br.unitins.topicos1.repository.PublisherRepository;
import br.unitins.topicos1.repository.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ComicServiceImpl implements ComicService{

    @Inject
    ComicRepository repository;

    @Inject
    PublisherRepository publisherRepository;

    @Inject
    AuthorRepository authorRepository;

    @Override
    @Transactional
    public ComicResponseDTO insert(ComicDTO dto) {
        Comic comic = new Comic();

        comic.setName(dto.name());
        comic.setPrice(dto.price());
        comic.setInventory(dto.inventory());
        comic.setNumPages(dto.numPages());
        comic.setBinding(Binding.valueOf(dto.binding()));
        
        comic.setPublisher(publisherRepository.findById(dto.publisher()));
        comic.setAuthor(authorRepository.findById(dto.author()));

        repository.persist(comic);

        return ComicResponseDTO.valueOf(comic);
    }

    @Override
    @Transactional
    public ComicResponseDTO update(Long id, ComicDTO dto) {
        Comic comic = repository.findById(id);

        comic.setName(dto.name());
        comic.setPrice(dto.price());
        comic.setInventory(dto.inventory());
        comic.setNumPages(dto.numPages());
        
        comic.setBinding(Binding.valueOf(dto.binding()));
        
        comic.setPublisher(publisherRepository.findById(dto.publisher()));

        comic.setAuthor(authorRepository.findById(dto.author()));

        return ComicResponseDTO.valueOf(comic);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    public List<ComicResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> ComicResponseDTO.valueOf(e)).toList();
    }

    @Override
    public ComicResponseDTO findById(Long id) {
        return ComicResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<ComicResponseDTO> findByName(String name) {
        // TODO Auto-generated method stub
        return repository.findByName(name).stream().map(e -> ComicResponseDTO.valueOf(e)).toList();
    }

}
