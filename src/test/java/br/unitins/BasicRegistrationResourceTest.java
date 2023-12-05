package br.unitins;

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

import br.unitins.topicos1.dto.LoginDTO;
import br.unitins.topicos1.service.UserService;

@QuarkusTest
public class BasicRegistrationResourceTest {
    
    @Inject UserService userService;

    @Test
    public void testInsertBasicUser(){
        LoginDTO dtoInsert = new LoginDTO("emailgenerico@mail.com", "12345");

        given().contentType(ContentType.JSON).body(dtoInsert).when().post("/basicUsers/insert/").then().statusCode(201);
    }

    @Test
    public void testInsertBasicUserNullEmail(){
        LoginDTO dtoInsert = new LoginDTO(null, "12345");

        given().contentType(ContentType.JSON).body(dtoInsert).when().post("/basicUsers/insert/").then().statusCode(400);
    }
}
