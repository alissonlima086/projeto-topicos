package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
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

    @Test
    public void testGetUserNotLogged(){
        given().when().get("/loggedUser").then().statusCode(401);
    }

    @Test
    public void testGetUser(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        given().header("Authorization", "Bearer " + token).when().get("/loggedUser").then().statusCode(200);
    }

    @Test
    public void testUpdateUsername(){
        UserDTO dto = new UserDTO("fulano", "fulano@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String newUsername = "teo";

        String token = jwtService.generateJwt(userService.findByEmail("fulano@mail.com"));

        //given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body("teo").when().patch("/update/username/").then().statusCode(201);
        //given().header("Authorization", "Bearer " + token)
        //.contentType(ContentType.JSON).body("teo")
        //.when().put("/update/username/").then().statusCode(204);

        UserResponseDTO user = userService.findByEmail("fulano@mail.com");

        assertThat(user.username(), is("teo"));
    }




    /*Set<String> roles = new HashSet<String>();
        
        roles.add(dto.profile().getLabel());

        return Jwt.issuer("unitins-jwt")
            .subject(dto.email())
            .groups(roles)
            .expiresAt(expiryDate)
            .sign();
             */
    
    
}
