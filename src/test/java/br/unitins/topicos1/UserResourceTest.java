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

    /*
    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        UserDTO dtoInsert = new UserDTO("fulano 2", "fulano2@mail.com", hashService.getHashPassword("12345"), 2);

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoInsert).when().post("/insert/user/").then().statusCode(201);
    }
    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("marquinho", "marcos@mail.com", "123456");
        
        UserResponseDTO userTest = service.insert(dto);
        Long id = userTest.id();

        UserDTO dtoUpdate = new UserDTO("marcos", "marcos.m@mail.com", "123456");

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/users/"+id).then().statusCode(204);

        UserResponseDTO verify = service.findById(id);
        assertThat(verify.username(), is("marcos"));
        assertThat(verify.email(), is("marcos.m@mail.com"));
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(dto);
        Long id = userTest.id();

        RestAssured.given().when().delete("/users/"+id).then().statusCode(204);
    }

    
    
    @Test
    public void testInsertPhone(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");
        
        UserResponseDTO userTest = service.insert(userDTO);
        Long id = userTest.id();

        PhoneDTO dto = new PhoneDTO("63", "96666-6666");
        given().contentType(ContentType.JSON).body(dto).when().post("/users/phone/insert/"+id).then().statusCode(201);

    }
    
    @Test
    public void testUpdatePhone(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(userDTO);
        Long idUser = userTest.id();

        PhoneDTO dto = new PhoneDTO("63", "96666-6666");

        PhoneResponseDTO phoneTest = service.insertPhone(idUser, dto);
        Long idPhone = phoneTest.id();

        PhoneDTO phoneUpdate = new PhoneDTO("63", "99999-9999");

        given().contentType(ContentType.JSON).body(phoneUpdate).when().put("/users/phone/update/"+idPhone).then().statusCode(204);
    }

    @Test
    public void testFindAllPhones(){
        given().when().get("/users/phone").then().statusCode(200);
    }

    @Test
    public void testFindByName(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(userDTO);
        String name = userTest.username();

        given().when().get("/users/search/name/"+name).then().statusCode(200);
    }

    @Test
    public void testFindById(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(userDTO);
        Long id = userTest.id();

        given().when().get("/users/"+id).then().statusCode(200);
    }
     */
}

