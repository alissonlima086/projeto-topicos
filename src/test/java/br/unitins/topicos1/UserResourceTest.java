package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

@QuarkusTest
public class UserResourceTest {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Test
    public void testFindAll(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        given().header("Authorization", "Bearer " + token).when().get("/users").then().statusCode(200);
    }

    @Test
    public void testFindAllNotLogger(){
        given().when().get("/users").then().statusCode(401);
    }

    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("fulano 2", "fulano2@mail.com", hashService.getHashPassword("12345"), 2);

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoInsert).when().post("/users/insert/user/").then().statusCode(201);
    }

    
    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("fulano 2", "fulano2@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("fulano2@mail.com").id();

        UserDTO dtoUpdate = new UserDTO("marcos", "marcos@mail.com", hashService.getHashPassword("12345"), 2);

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoUpdate).when().put("/users/update/user/"+idUser).then().statusCode(204);
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("fulano 2", "fulano2@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("fulano2@mail.com").id();

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/users/delete/user/"+idUser).then().statusCode(204);
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

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(phone).when().post("/users/phone/insert/"+ idUser).then().statusCode(201);

    }
    

    @Test
    public void testUpdatePhone(){
        UserDTO dto = new UserDTO("fulano", "fulano6@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("fulano6@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO("63", "777777777");

        userService.insertPhone(idUser, phone);

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        List<PhoneResponseDTO> idPhone = userService.findPhoneByUserId(idUser);

        PhoneDTO phoneUpdate = new PhoneDTO("63", "99999-9999");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(phoneUpdate).when().put("/users/phone/update/"+1).then().statusCode(204);
    }

    @Test
    public void testFindAllPhones(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        given().header("Authorization", "Bearer " + token).given().when().get("/users/phone").then().statusCode(200);
    }

    /*

    @Test
    public void testFindByName(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(userDTO);
        String name = userTest.username();

        given().when().get("/users/search/name/"+name).then().statusCode(200);
    }

    /*

    @Test
    public void testFindById(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(userDTO);
        Long id = userTest.id();

        given().when().get("/users/"+id).then().statusCode(200);
    }
     */
}

