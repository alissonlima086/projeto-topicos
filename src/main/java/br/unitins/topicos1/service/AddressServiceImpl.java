package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AddressDTO;
import br.unitins.topicos1.dto.AddressResponseDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.model.Address;
import br.unitins.topicos1.repository.AddressRepository;
import br.unitins.topicos1.repository.CityRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import br.unitins.topicos1.validation.ValidationException;

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



        if(dto.name().isEmpty()){
            throw new ValidationException("400", "O endereço deve possuir um nome");
        } else if(dto.postalCode().isEmpty() || dto.address().isEmpty()){
            throw new ValidationException("400", "Os valores de CEP e Endereço devem ser informados");
        }

        Address address = new Address();

        address.setName(dto.name());
        address.setPostalCode(dto.postalCode());
        address.setAddress(dto.address());
        address.setComplement(dto.complement());

        if(dto.user() == 0 || dto.user() == null){
            throw new ValidationException("400", "O usuario deve ser informado");
        } else if(dto.city() == 0 || dto.city() == null){
            throw new ValidationException("400", "A cidade deve ser informada");
        }
        
        address.setCity(cityRepository.findById(dto.city()));
        address.setUser(userRepository.findById(dto.user()));


        repository.persist(address);

        return AddressResponseDTO.valueOf(address);

    }

    @Override
    @Transactional
    public AddressResponseDTO update(Long id, AddressDTO dto) {
        if(repository.findById(id) == null) {
            throw new NotFoundException("Endereço não encontrado");
        }

        if(dto.name().isEmpty()){
            throw new ValidationException("400", "O endereço deve possuir um nome");
        } else if(dto.postalCode().isEmpty() || dto.address().isEmpty()){
            throw new ValidationException("400", "Os valores de CEP e Endereço devem ser informados");
        }


        Address address = repository.findById(id);

        address.setName(dto.name());
        address.setPostalCode(dto.postalCode());
        address.setAddress(dto.address());
        address.setComplement(dto.complement());

        if(dto.user() == 0 || dto.user() == null){
            throw new ValidationException("400", "O usuario deve ser informado");
        } else if(dto.city() == 0 || dto.city() == null){
            throw new ValidationException("400", "A cidade deve ser informada");
        }
        
        
        address.setCity(cityRepository.findById(dto.city()));
        address.setUser(userRepository.findById(dto.user()));


        return AddressResponseDTO.valueOf(address);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException("Usuario não encontrado");
        }
    }

    @Override
    public List<AddressResponseDTO> findAll() {
        if(repository.listAll().stream().map(e -> AddressResponseDTO.valueOf(e)).toList().isEmpty()){
            throw new NotFoundException("Não há endereços");
        }
        return repository.listAll().stream().map(e -> AddressResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AddressResponseDTO> findByCity(Long city) {
        List<Address> addresses = repository.findByCity(city);
        if(cityRepository.findById(city) == null) {
            throw new NotFoundException("Cidade não encontrada");
        } else if(addresses.isEmpty()){
            throw new NotFoundException("Não há endereços nessa cidade");
        }
        return addresses.stream().map(AddressResponseDTO::valueOf).toList();
    }
}
