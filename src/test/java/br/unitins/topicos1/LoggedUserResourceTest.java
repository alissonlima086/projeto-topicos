package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.EmailDTO;
import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.UpdatePasswordDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.dto.UsernameDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.resource.AuthResource;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.UserService;

@QuarkusTest
public class LoggedUserResourceTest {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Test
    public void testGetUserNotLogged(){
        given().when().get("/loggedUser").then().statusCode(401);
    }

    @Test
    public void testGetUser(){
        UserDTO dto = new UserDTO("fulano", "fulano1@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("fulano1@mail.com"));

        given().header("Authorization", "Bearer " + token).when().get("/loggedUser").then().statusCode(200);
    }

    @Test
    public void testUpdateUsername(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("fulano", "fulano2@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //Inserindo o novo username
        UsernameDTO usernameDTO = new UsernameDTO("teo");

        //Pegando Id
        Long idUser = userService.findByEmail("fulano2@mail.com").id();

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        //Testando status code
        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(usernameDTO).when().put("/loggedUser/update/username/").then().statusCode(204);

        //verificando os dados
        UserResponseDTO user = userService.findById(idUser);
        //assertThat(user.username(), is(usernameDTO.username()));
    }


    @Test
    public void testUpdateEmail(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("fulano", "fulano3@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //Inserindo o novo email
        EmailDTO emailDTO = new EmailDTO("novo@mail.com");
        
        Long idUser = userService.findByEmail("fulano3@mail.com").id();

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        //Testando status code
        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(emailDTO).when().put("/loggedUser/update/email/").then().statusCode(204);

        //verificando os dados
        UserResponseDTO user = userService.findById(idUser);
        //assertThat(usernameAtual, is(usernameDTO.username()));
    }

    @Test
    public void testUpdatePassword(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("fulano", "fulano4@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //Inserindo a senha atual e a nova senha
        UpdatePasswordDTO passwordDTO = new UpdatePasswordDTO(hashService.getHashPassword("12345"), "senhateste");

        //pegando Id do usuario
        Long idUser = userService.findByEmail("fulano4@mail.com").id();

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        //Testando status code
        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(passwordDTO).when().put("/loggedUser/update/password/").then().statusCode(204);

        //verificando os dados
        UserResponseDTO user = userService.findById(idUser);
        //assertThat(usernameAtual, is(usernameDTO.username()));
    }

    @Test
    public void testGetPhone(){
        UserDTO dto = new UserDTO("fulano", "fulano5@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("fulano5@mail.com"));

        given().header("Authorization", "Bearer " + token).get("/loggedUser/phone/").then().statusCode(200);

    }

    @Test
    public void testInsertPhone(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("fulano", "fulano6@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("fulano6@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO("63", "777777777");

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(phone).when().post("/loggedUser/insert/phone/").then().statusCode(200);

    }

    
}
