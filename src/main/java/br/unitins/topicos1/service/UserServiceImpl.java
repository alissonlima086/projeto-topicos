package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.model.Phone;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.AddressRepository;
import br.unitins.topicos1.repository.PhoneRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository repository;

    @Inject
    AddressRepository addressRepository;

    @Inject
    PhoneRepository phoneRepository;

    @Override
    @Transactional
    public UserResponseDTO insert(UserDTO dto) {
        User newUser = new User();

        newUser.setUsername(dto.username());
        newUser.setEmail(dto.email());
        newUser.setPassword(dto.password());

        repository.persistAndFlush(newUser);

        return UserResponseDTO.valueOf(newUser);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserDTO dto) {
        User user = repository.findById(id);
        user.setUsername(dto.username());
        user.setEmail(dto.email());

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public PhoneResponseDTO insertPhone(Long id, PhoneDTO dto){
        Phone phone = new Phone();
        
        phone.setAreaCode(dto.areaCode());
        phone.setNumber(dto.number());
        phone.setUser(repository.findById(id));

        phoneRepository.persist(phone);

        return PhoneResponseDTO.valueOf(phone);
    }

    @Override
    @Transactional
    public PhoneResponseDTO updatePhone(Long id, PhoneDTO dto){
        Phone phone = phoneRepository.findById(id);
        
        phone.setAreaCode(dto.areaCode());
        phone.setNumber(dto.number());

        return PhoneResponseDTO.valueOf(phone);
    }

    @Override
    public List<PhoneResponseDTO> findAllPhones() {
        return phoneRepository.listAll().stream().map(e -> PhoneResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UserResponseDTO findById(long id) {
        return UserResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<UserResponseDTO> findByUsername(String username) {
        return repository.findByUsername(username).stream().map(e -> UserResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> UserResponseDTO.valueOf(e)).toList();
    }
    
}
