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
        UserDTO dto = new UserDTO("fulano", "jason@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("fulano 2", "fulano2@mail.com", hashService.getHashPassword("12345"), 2);

        String token = jwtService.generateJwt(userService.findByEmail("jason@mail.com"));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoInsert).when().post("/users/insert/user/").then().statusCode(201);
    }

    
    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("fulano", "may@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("fulano 2", "junior@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("junior@mail.com").id();

        UserDTO dtoUpdate = new UserDTO("marcos", "marcos@mail.com", hashService.getHashPassword("12345"), 2);

        String token = jwtService.generateJwt(userService.findByEmail("may@mail.com"));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoUpdate).when().put("/users/update/user/"+idUser).then().statusCode(204);
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("mary", "mary@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("min", "min@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("min@mail.com").id();

        String token = jwtService.generateJwt(userService.findByEmail("mary@mail.com"));

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/users/delete/user/"+idUser).then().statusCode(204);
    }

    
    @Test
    public void testInsertPhone(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("jose", "jose@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("jose@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO("63", "777777777");

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(phone).when().post("/users/phone/insert/"+ idUser).then().statusCode(201);

    }
    

    @Test
    public void testUpdatePhone(){
        UserDTO dto = new UserDTO("fulano", "jota@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("jota@mail.com").id();

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
        UserDTO dto = new UserDTO("fulano", "dayse@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("dayse@mail.com"));

        given().header("Authorization", "Bearer " + token).given().when().get("/users/phone").then().statusCode(200);
    }

    @Test
    public void testFindByUsername(){
        UserDTO dto = new UserDTO("fulano", "sol@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("lisbela", "maria@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        String name = "lisbela";

        String token = jwtService.generateJwt(userService.findByEmail("sol@mail.com"));

        given().header("Authorization", "Bearer " + token).given().when().get("/users/search/name/"+ name).then().statusCode(200);
    }

    @Test
    public void testFindById(){
        UserDTO dto = new UserDTO("fulano", "midas@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("Maria", "maria@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("maria@mail.com").id();

        String token = jwtService.generateJwt(userService.findByEmail("midas@mail.com"));

        given().header("Authorization", "Bearer " + token).when().get("/users/"+idUser).then().statusCode(200);
    }


    // --------------- Não logados --------------

    @Test
    public void testInsertNotLogged(){

        UserDTO dtoInsert = new UserDTO("mario", "mario@mail.com", hashService.getHashPassword("12345"), 2);

        given().contentType(ContentType.JSON).body(dtoInsert).when().post("/users/insert/user/").then().statusCode(401);
    }

    @Test
    public void testUpdateNotLogged(){

        UserDTO dtoInsert = new UserDTO("jon", "jon@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("jon@mail.com").id();

        UserDTO dtoUpdate = new UserDTO("john", "john@mail.com", hashService.getHashPassword("12345"), 2);

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/users/update/user/"+idUser).then().statusCode(401);
    }

    @Test
    public void testDeleteNotLogged(){

        UserDTO dtoInsert = new UserDTO("manoel", "manoel@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("manoel@mail.com").id();

        RestAssured.given().when().delete("/users/delete/user/"+idUser).then().statusCode(401);
    }
    
    @Test
    public void testInsertPhoneNotLogged(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("gabriel", "gabriel@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userInsert = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("gabriel@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO("63", "777777777");


        given().contentType(ContentType.JSON).body(phone).when().post("/users/phone/insert/"+ idUser).then().statusCode(401);

    }

    @Test
    public void testUpdatePhoneNotLogged(){
        UserDTO dto = new UserDTO("miguel", "miguel@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("miguel@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO("63", "777777777");

        userService.insertPhone(idUser, phone);

        List<PhoneResponseDTO> idPhone = userService.findPhoneByUserId(idUser);

        PhoneDTO phoneUpdate = new PhoneDTO("63", "99999-9999");

        given().contentType(ContentType.JSON).body(phoneUpdate).when().put("/users/phone/update/"+1).then().statusCode(401);
    }



    @Test
    public void testFindAllPhonesNotLogged(){
        given().given().when().get("/users/phone").then().statusCode(401);
    }

    @Test
    public void testFindByUsernameNotLogged(){
        UserDTO dtoInsert = new UserDTO("fernando", "fernando@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        String name = "fernando";

        given().given().when().get("/users/search/name/"+ name).then().statusCode(401);
    }


    @Test
    public void testFindByIdNotLogged(){
        UserDTO dtoInsert = new UserDTO("tina", "tina@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userInsert = userService.insert(dtoInsert);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("tina@mail.com").id();

        given().when().get("/users/"+idUser).then().statusCode(401);
    }
}

