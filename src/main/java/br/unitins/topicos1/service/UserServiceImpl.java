package br.unitins.topicos1.service;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.CompleteUserDTO;
import br.unitins.topicos1.dto.CompleteUserResponseDTO;
import br.unitins.topicos1.dto.EmailDTO;
import br.unitins.topicos1.dto.LoginDTO;
import br.unitins.topicos1.dto.LoginResponseDTO;
import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.PhysicalPersonDTO;
import br.unitins.topicos1.dto.PhysicalPersonResponseDTO;
import br.unitins.topicos1.dto.UpdatePasswordDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.dto.UsernameDTO;
import br.unitins.topicos1.model.Gender;
import br.unitins.topicos1.model.Phone;
import br.unitins.topicos1.model.PhysicalPerson;
import br.unitins.topicos1.model.Profile;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.AddressRepository;
import br.unitins.topicos1.repository.PhoneRepository;
import br.unitins.topicos1.repository.PhysicalPersonRepository;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.resource.AuthResource;
//import br.unitins.topicos1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import br.unitins.topicos1.validation.ValidationException;
import io.quarkus.security.ForbiddenException;

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
    public LoginResponseDTO insertBasicUser(LoginDTO dto){
        if(dto.email() == null || dto.email().isEmpty()){
            throw new ValidationException("400", "O email não pode estar em branco");
        } else if(dto.password() == null || dto.password().isEmpty()){
            throw new ValidationException("400", "A senha não pode estar em branco");
        }

        if(repository.existsByEmail(dto.email())){
            throw new ValidationException("400", "O email já existe");
        }

        User newUser = new User();

        newUser.setEmail(dto.email());

        newUser.setPassword(hashService.getHashPassword(dto.password()));

        newUser.setProfile(Profile.valueOf(1));

        repository.persist(newUser);

        return LoginResponseDTO.valueOf(newUser);
    }

    @Override
    @Transactional
    public UserResponseDTO insert(UserDTO dto) {

        if(dto.email() == null || dto.email().isEmpty() || dto.password() == null || dto.password().isEmpty()) {
            throw new ValidationException("400", "Email e senha não podem estar em branco");
        }

        if(Profile.valueOf(dto.profile()) == null){
            throw new ValidationException("400", "O tipo de perfil não pode ser nulo");
        }

        User newUser = new User();

        newUser.setUsername(dto.username());
        newUser.setEmail(dto.email());

        newUser.setProfile(Profile.valueOf(dto.profile()));

        newUser.setPassword(hashService.getHashPassword(dto.password()));

        repository.persist(newUser);

        return UserResponseDTO.valueOf(newUser);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserDTO dto) {
        if(repository.findById(id) == null){
            throw new NotFoundException("Usuario não encontrado");
        }

        if(dto.email() == null || dto.email().isEmpty() || dto.password() == null || dto.password().isEmpty()) {
            throw new ValidationException("400", "Email e senha não podem estar em branco");
        }

        if(Profile.valueOf(dto.profile()) == null){
            throw new ValidationException("400", "O tipo de perfil não pode ser nulo");
        }

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
            throw new NotFoundException("Usuario não encontrado");
        }
    }

    // ---------- PHONE ----------

    @Override
    @Transactional
    public PhoneResponseDTO insertPhone(Long id, PhoneDTO dto){

        if(dto.areaCode() == null || dto.areaCode().isEmpty() || dto.number() == null || dto.number().isEmpty()){
            throw new ValidationException("400", "O codigo de area e o número devem possuir um valor");
        }

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

        if(repository.findById(id) == null){
            throw new NotFoundException("Usuario não encontrado");
        }

        if(dto.areaCode() == null || dto.areaCode().isEmpty() || dto.number() == null || dto.number().isEmpty()){
            throw new ValidationException("400", "O codigo de area e o número devem possuir um valor");
        }


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
            throw new NotFoundException("Telefone não encontrado");
        }
    }


    // ---------- dados completos ----------

    @Override
    @Transactional
    public UsernameDTO insertUsername(Long id, UsernameDTO usernameDTO){
        User user = repository.findById(id);

        if(user.getUsername() != null){
            throw new IllegalArgumentException("O usuário já possui um nome, tente alterar o nome.");
        }

        if(usernameDTO.username() == null){
            throw new ValidationException("400", "O valor fornecido é invalido");
        }

        if(repository.existsByUsername(usernameDTO.username())){
            throw new ValidationException("400", "O nome de usuario já existe");
        }

        user.setUsername(usernameDTO.username());

        return UsernameDTO.valueOf(user);
    }

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

        if(user.getPhysicalPerson() != null){
            throw new IllegalArgumentException("Estes dados não podem ser alterados");
        }
        
        PhysicalPerson person = new PhysicalPerson();

        person.setName(dto.fullName().replaceAll("\\s", ""));
        person.setCpf(dto.cpf());
        person.setGender(Gender.valueOf(dto.gender()));
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
    public UserResponseDTO updateUsername(String login, UsernameDTO newUsername) {

        if(newUsername.username() == null || newUsername.username().isEmpty()){
            throw new ValidationException("400", "O valor fornecido é invalido");
        }

        if(repository.existsByUsername(newUsername.username())){
            throw new ValidationException("400", "O nome de usuario já existe");
        }

        LOG.info("Iniciando update do username");

        User user = repository.findByEmail(login);

        if(user != null){
            LOG.info("Usuario encontrado");
            user.setUsername(newUsername.username().replaceAll("^\"|\"$", ""));
        } else{
            LOG.info("Usuario nao encontrado");
        }

        LOG.info("Update do username concluido");

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateEmail(String login, EmailDTO newEmail) {

        if(newEmail.email() == null|| newEmail.email().isEmpty()){
            throw new ValidationException("400", "O valor fornecido é invalido");
        }

        if(repository.existsByEmail(login)){
            throw new ValidationException("400", "O email já existe");
        }
        
        LOG.info("Iniciando update do email");

        User user = repository.findByEmail(login);

        if(user != null){
            LOG.info("Usuario encontrado");
            user.setEmail(newEmail.email().replaceAll("^\"|\"$", ""));
        } else{
            LOG.info("Usuario nao encontrado");
        }

        LOG.info("Update do email concluido");

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updatePassword(String login, UpdatePasswordDTO updatePassword) {

        if(updatePassword.newPassword() == null || updatePassword.newPassword().isEmpty()){
            throw new ValidationException("400", "O valor fornecido é invalido");
        }

        LOG.info("Iniciando update password");

        User user = repository.findByEmail(login);

        if(!hashService.getHashPassword(updatePassword.currentPassword()).equals(user.getPassword())){
            throw new ForbiddenException("Acesso negado. A senha informada é incorreta");
        }

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
        if(phoneRepository.listAll().stream().map(e -> PhoneResponseDTO.valueOf(e)).toList().isEmpty()){
            throw new NotFoundException("Não há telefones");
        }
        return phoneRepository.listAll().stream().map(e -> PhoneResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UserResponseDTO findById(long id) {
        if(repository.findById(id) == null) {
            throw new NotFoundException("Usuario não encontrado");
        }
        return UserResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        if(repository.findByUsername(username) == null) {
            throw new NotFoundException("Usuario não encontrado");
        }
        return UserResponseDTO.valueOf(repository.findByUsername(username));
    }

    @Override
    public List<UserResponseDTO> findAll() {
        if(repository.listAll().stream().map(e -> UserResponseDTO.valueOf(e)).toList().isEmpty()){
            throw new NotFoundException("Não há usuarios");
        }
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
        if(repository.findById(id) == null){
            throw new NotFoundException("Usuario não encontrado");
        } else if(phoneRepository.findPhoneByUserId(id).stream().map(e -> PhoneResponseDTO.valueOf(e)).toList().isEmpty()){
            throw new NotFoundException("Não há telefones");
        }
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
