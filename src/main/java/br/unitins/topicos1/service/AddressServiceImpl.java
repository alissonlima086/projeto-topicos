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
import jakarta.ws.rs.NotAuthorizedException;
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

    @Inject
    UserService userService;

    @Override
    @Transactional
    public AddressResponseDTO insert(Long id, AddressDTO dto) {

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

        if(dto.city() == 0 || dto.city() == null){
            throw new ValidationException("400", "A cidade deve ser informada");
        }
        
        address.setCity(cityRepository.findById(dto.city()));
        address.setUser(userRepository.findById(id));


        repository.persist(address);

        return AddressResponseDTO.valueOf(address);

    }

    @Override
    @Transactional
    public AddressResponseDTO update(Long idAddress, Long idUser, AddressDTO dto) {
        if(repository.findById(idAddress) == null) {
            throw new NotFoundException("Endereço não encontrado");
        }

        if(dto.name().isEmpty()){
            throw new ValidationException("400", "O endereço deve possuir um nome");
        } else if(dto.postalCode().isEmpty() || dto.address().isEmpty()){
            throw new ValidationException("400", "Os valores de CEP e Endereço devem ser informados");
        }

        if(dto.city() == 0 || dto.city() == null){
            throw new ValidationException("400", "A cidade deve ser informada");
        }

        Address address = repository.findById(idAddress);

        if(address.getUser().getId() == userService.findById(idUser).id()){
            address.setName(dto.name());
            address.setPostalCode(dto.postalCode());
            address.setAddress(dto.address());
            address.setComplement(dto.complement());
            address.setCity(cityRepository.findById(dto.city()));
            address.setUser(userRepository.findById(idUser));
        } else{
            throw new NotAuthorizedException("Você não pode fazer essa alteração");
        }

        return AddressResponseDTO.valueOf(address);
    }

    @Override
    @Transactional
    public void delete(Long idAddress, Long idUser) {

        if(repository.findById(idAddress) == null){
            throw new NotFoundException("Endereço não encontrado");
        }

        if(repository.findById(idAddress).getUser().getId() == userService.findById(idUser).id()){
            if(!repository.deleteById(idAddress)){
                throw new NotFoundException("Usuario não encontrado");
            }
        } else{
            throw new NotAuthorizedException("Você não pode concluir essa ação");
        }
        
    }

    @Override
    public List<AddressResponseDTO> findAll(Long id) {
        List<Address> addresses = repository.findByUser(id);

        if (addresses.isEmpty()) {
            throw new NotFoundException("Não há endereços");
        }

        return addresses.stream().map(AddressResponseDTO::valueOf).toList();
    }

    @Override
    public List<AddressResponseDTO> findByUserAndCity(Long user, Long city) {

        List<Address> addresses = repository.findByUserAndCity(user, city);

        Address addressRef = repository.findById(addresses.get(1).getUser().getId());

        if(addressRef.getId() != userService.findById(user).id()){
            throw new NotAuthorizedException("Você não pode concluir essa ação");

        }
        if(cityRepository.findById(city) == null) {
            throw new NotFoundException("Cidade não encontrada");
        } else if(addresses.isEmpty()){
            throw new NotFoundException("Não há endereços nessa cidade");
        }
        return addresses.stream().map(AddressResponseDTO::valueOf).toList();
    }

    // ---------- Logged User ----------------------------------------------------------------


}
