package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PublisherDTO;
import br.unitins.topicos1.dto.PublisherResponseDTO;
import br.unitins.topicos1.model.Publisher;
import br.unitins.topicos1.repository.PublisherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PublisherServiceImpl implements PublisherService{

    @Inject
    PublisherRepository repository;

    @Override
    @Transactional
    public PublisherResponseDTO insert(PublisherDTO dto) {
        Publisher Publisher = new Publisher();

        Publisher.setName(dto.name());

        repository.persist(Publisher);

        return PublisherResponseDTO.valueOf(Publisher);
    }

    @Override
    @Transactional
    public PublisherResponseDTO update(Long id, PublisherDTO dto) {
        Publisher Publisher = repository.findById(id);

        Publisher.setName(dto.name());

        return PublisherResponseDTO.valueOf(Publisher);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    public PublisherResponseDTO findById(Long id) {
        return PublisherResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<PublisherResponseDTO> findByName(String name) {
        return repository.findByName(name).stream().map(e -> PublisherResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PublisherResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> PublisherResponseDTO.valueOf(e)).toList();
    }
    
}