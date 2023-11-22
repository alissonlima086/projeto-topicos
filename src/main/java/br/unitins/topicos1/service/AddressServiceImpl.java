package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AddressDTO;
import br.unitins.topicos1.dto.AddressResponseDTO;
import br.unitins.topicos1.model.Address;
import br.unitins.topicos1.repository.AddressRepository;
import br.unitins.topicos1.repository.CityRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AddressServiceImpl implements AddressService{

    @Inject
    AddressRepository repository;

    @Inject
    CityRepository cityRepository;

    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public AddressResponseDTO insert(AddressDTO dto) {
        Address address = new Address();
        

        address.setName(dto.name());
        address.setPostalCode(dto.postalCode());
        address.setAddress(dto.address());
        address.setComplement(dto.complement());
        
        address.setCity(cityRepository.findById(dto.city()));
        address.setUser(userRepository.findById(dto.user()));

        repository.persist(address);

        return AddressResponseDTO.valueOf(address);

    }

    @Override
    @Transactional
    public AddressResponseDTO update(Long id, AddressDTO dto) {
        Address address = repository.findById(id);

        address.setName(dto.name());
        address.setPostalCode(dto.postalCode());
        address.setAddress(dto.address());
        address.setComplement(dto.complement());

        return AddressResponseDTO.valueOf(address);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    public List<AddressResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> AddressResponseDTO.valueOf(e)).toList();
    }
}
