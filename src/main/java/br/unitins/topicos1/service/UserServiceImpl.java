package br.unitins.topicos1.service;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.CompleteUserDTO;
import br.unitins.topicos1.dto.CompleteUserResponseDTO;
import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.PhysicalPersonDTO;
import br.unitins.topicos1.dto.PhysicalPersonResponseDTO;
import br.unitins.topicos1.dto.UpdatePasswordDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.model.Phone;
import br.unitins.topicos1.model.PhysicalPerson;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.AddressRepository;
import br.unitins.topicos1.repository.PhoneRepository;
import br.unitins.topicos1.repository.PhysicalPersonRepository;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.resource.AuthResource;
import br.unitins.topicos1.validation.ValidationException;
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

    @Inject
    HashService hashService;

    @Inject
    PhysicalPersonService personService;

    @Inject
    PhysicalPersonRepository personRepository;

    @Inject
    JwtService jwtService;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Override
    @Transactional
    public UserResponseDTO insert(UserDTO dto) {
        User newUser = new User();

        newUser.setUsername(dto.username());
        newUser.setEmail(dto.email());

        newUser.setPassword(hashService.getHashPassword(dto.password()));

        repository.persistAndFlush(newUser);

        return UserResponseDTO.valueOf(newUser);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserDTO dto) {
        User user = repository.findById(id);
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(hashService.getHashPassword(dto.password()));

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    // ---------- PHONE ----------

    @Override
    @Transactional
    public PhoneResponseDTO insertPhone(Long id, PhoneDTO dto){

        LOG.info("Inserindo Phone");

        Phone phone = new Phone();
        
        phone.setAreaCode(dto.areaCode());
        phone.setNumber(dto.number());
        
        User user = repository.findById(id);

        phone.setUser(user);
        
        user.getPhones().add(phone);

        LOG.info("Phone inserido na lista de usuario");

        phoneRepository.persist(phone);

        LOG.info("Phone inserido");

        return PhoneResponseDTO.valueOf(phone);
    }


    @Override
    @Transactional
    public PhoneResponseDTO updatePhone(Long id, PhoneDTO dto){

        LOG.info("Iniciando update de phone");

        Phone phone = phoneRepository.findById(id);

        if(phone != null){
            LOG.info("Phone encontrado");
            phone.setAreaCode(dto.areaCode());
            phone.setNumber(dto.number());

            LOG.info("Phone atualizado");
        } else{
            LOG.info("Phone não encontrado");
        }

        return PhoneResponseDTO.valueOf(phone);
    }

    @Override
    public void deletePhone(Long id){
        if(!phoneRepository.deleteById(id)){
            throw new NotFoundException();
        }
    }


    // ---------- dados completos ----------

    @Override
    public List<CompleteUserResponseDTO> findAllCompleteUsers() {
        return repository.listAll().stream().map(e -> CompleteUserResponseDTO.valueOf(e)).toList();
    }

    @Override
    public CompleteUserResponseDTO findCompleteUserByEmail(String email) {
        return CompleteUserResponseDTO.valueOf(repository.findByEmail(email));
    }


    @Override
    @Transactional
    public CompleteUserResponseDTO completeUser(Long id, CompleteUserDTO dto) {

        User user = repository.findById(id);
        
        PhysicalPerson person = new PhysicalPerson();

        person.setName(dto.fullName().replaceAll("\\s", ""));
        person.setCpf(dto.cpf());
        person.setGender(CompleteUserResponseDTO.valueOf(user).gender());
        person.setUser(user);

        personRepository.persist(person);

        user.setPhysicalPerson(person);
        
        return  CompleteUserResponseDTO.valueOf(user);
    }

     // ---------- UPDATE ----------

    @Override
    public UserResponseDTO updateImageName(Long id, String imageName) {
        User user = repository.findById(id);
        user.setImageName(imageName);
        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUsername(String login, String newUsername) {

        LOG.info("Iniciando update do username");

        newUsername = newUsername.replaceAll("^\"|\"$", "");

        User user = repository.findByEmail(login);

        if(user != null){
            LOG.info("Usuario encontrado");
            user.setUsername(newUsername);
        } else{
            LOG.info("Usuario nao encontrado");
        }

        LOG.info("Update do username concluido");

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateEmail(String login, String newEmail) {
        
        LOG.info("Iniciando update do email");

        newEmail = newEmail.replaceAll("^\"|\"$", "");

        User user = repository.findByEmail(login);

        if(user != null){
            LOG.info("Usuario encontrado");
            user.setEmail(newEmail);
        } else{
            LOG.info("Usuario nao encontrado");
        }

        LOG.info("Update do email concluido");

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updatePassword(String login, UpdatePasswordDTO updatePassword) {

        LOG.info("Iniciando update password");

        User user = repository.findByEmail(login);

        if(user != null) {
            LOG.info("Usuario encontrado");

            if(hashService.getHashPassword(updatePassword.currentPassword()).equals(user.getPassword())){
                user.setPassword(hashService.getHashPassword(updatePassword.newPassword()));
                LOG.info("Update Password concluido");
            }

        } else{
            LOG.info("Usuario nao encontrado");
        }


        return UserResponseDTO.valueOf(user);
    }



    // ---------- FIND ----------

    @Override
    public List<PhoneResponseDTO> findAllPhones() {
        return phoneRepository.listAll().stream().map(e -> PhoneResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UserResponseDTO findById(long id) {
        return UserResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        return UserResponseDTO.valueOf(repository.findByUsername(username));
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> UserResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UserResponseDTO findByEmailAndPassword(String email, String password) {
        User user = repository.findByEmailAndPassword(email, password);
        if (user == null) 
            throw new ValidationException("login", "Login ou senha inválido");
        
        return UserResponseDTO.valueOf(user);
    }

    @Override
    public List<PhoneResponseDTO> findPhoneByUserId(Long id) {
        return phoneRepository.findPhoneByUserId(id).stream().map(e -> PhoneResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        User user = repository.findByEmail(email);
        if(email == null){
            throw new ValidationException("email", "Email invalido");
        }

        return UserResponseDTO.valueOf(user);
    }

    
    
}
