package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;
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
        Comic Comic = new Comic();
        

        Comic.setNumPages(dto.numPages());
        Comic.setBinding(dto.binding());
        
        Comic.setPublisher(publisherRepository.findById(dto.publisher()));

        Comic.setAuthor(authorRepository.findById(dto.author()));

        repository.persist(Comic);

        return ComicResponseDTO.valueOf(Comic);

    }

    @Override
    @Transactional
    public ComicResponseDTO update(Long id, ComicDTO dto) {
        Comic Comic = repository.findById(id);

        Comic.setNumPages(dto.numPages());
        Comic.setBinding(dto.binding());
        
        Comic.setPublisher(publisherRepository.findById(dto.publisher()));

        Comic.setAuthor(authorRepository.findById(dto.author()));

        return ComicResponseDTO.valueOf(Comic);
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

    
}
