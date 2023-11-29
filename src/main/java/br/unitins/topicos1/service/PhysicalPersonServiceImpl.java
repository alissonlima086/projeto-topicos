package br.unitins.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.topicos1.dto.PhysicalPersonDTO;
import br.unitins.topicos1.dto.PhysicalPersonResponseDTO;
import br.unitins.topicos1.model.PhysicalPerson;
import br.unitins.topicos1.model.Gender;
import br.unitins.topicos1.repository.PhysicalPersonRepository;

@ApplicationScoped
public class PhysicalPersonServiceImpl implements PhysicalPersonService {

    @Inject
    PhysicalPersonRepository physicalPersonRepository;

    @Inject
    Validator validator;

    @Override
    public List<PhysicalPersonResponseDTO> findAll() {
        List<PhysicalPerson> list = physicalPersonRepository.listAll();
        return list.stream().map(PhysicalPersonResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PhysicalPersonResponseDTO findById(Long id) {
        PhysicalPerson physicalPerson = physicalPersonRepository.findById(id);
        if (physicalPerson == null)
            throw new NotFoundException("Pessoa Fisica não encontrado.");
        return new PhysicalPersonResponseDTO(physicalPerson);
    }

    @Override
    @Transactional
    public PhysicalPersonResponseDTO create(PhysicalPersonDTO physicalPersonDto) throws ConstraintViolationException {
        validar(physicalPersonDto);

        PhysicalPerson entity = new PhysicalPerson();
        entity.setName(physicalPersonDto.name());
        entity.setCpf(physicalPersonDto.cpf());
        entity.setGender(Gender.valueOf(physicalPersonDto.gender()));

        physicalPersonRepository.persist(entity);

        return new PhysicalPersonResponseDTO(entity);
    }

    public PhysicalPerson createPhysicalPerson(PhysicalPersonDTO physicalPersonDto) throws ConstraintViolationException {
        validar(physicalPersonDto);

        PhysicalPerson entity = new PhysicalPerson();
        entity.setName(physicalPersonDto.name());
        entity.setCpf(physicalPersonDto.cpf());
        entity.setGender(Gender.valueOf(physicalPersonDto.gender()));

        physicalPersonRepository.persist(entity);

        return entity;
    }

    @Override
    @Transactional
    public PhysicalPersonResponseDTO update(Long id, PhysicalPersonDTO physicalPersonDto)
            throws ConstraintViolationException {
        validar(physicalPersonDto);

        PhysicalPerson entity = physicalPersonRepository.findById(id);

        entity.setName(physicalPersonDto.name());
        entity.setCpf(physicalPersonDto.cpf());
        entity.setGender(Gender.valueOf(physicalPersonDto.gender()));

        return new PhysicalPersonResponseDTO(entity);
    }

    private void validar(PhysicalPersonDTO physicalPersonDto) throws ConstraintViolationException {
        Set<ConstraintViolation<PhysicalPersonDTO>> violations = validator.validate(physicalPersonDto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        physicalPersonRepository.deleteById(id);
    }

    @Override
    public List<PhysicalPersonResponseDTO> findByName(String name) {
        List<PhysicalPerson> list = physicalPersonRepository.findByName(name);
        return list.stream().map(PhysicalPersonResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return physicalPersonRepository.count();
    }

}
