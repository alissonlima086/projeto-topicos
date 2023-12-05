package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;

import br.unitins.topicos1.dto.LoginDTO;

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

    @Test
    public void testInsertBasicUserNullPassword(){
        LoginDTO dtoInsert = new LoginDTO("generic@mail.com", null);

        given().contentType(ContentType.JSON).body(dtoInsert).when().post("/basicUsers/insert/").then().statusCode(400);
    }
}
