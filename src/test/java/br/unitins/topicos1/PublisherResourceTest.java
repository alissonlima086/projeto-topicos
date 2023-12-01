package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.PublisherDTO;
import br.unitins.topicos1.dto.PublisherResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.PublisherService;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class PublisherResourceTest {

    @Inject
    PublisherService service;

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
        given().when().get("/publishers").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        UserDTO dto = new UserDTO("alisa", "alisa@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("alisa@mail.com"));

        PublisherDTO dtoPublisher = new PublisherDTO("Marvel");

        PublisherResponseDTO publisherTest = service.insert(dtoPublisher);
        Long id = publisherTest.id();

        given().header("Authorization", "Bearer " + token).when().get("/publishers/search/id/"+id).then().statusCode(200);
    }

    @Test
    public void testFindByNome(){
        UserDTO dto = new UserDTO("kazuya", "kazuya@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("kazuya@mail.com"));

        PublisherDTO dtoPublisher = new PublisherDTO("DC");

        PublisherResponseDTO publisherTest = service.insert(dtoPublisher);
        String name = publisherTest.name();

        given().header("Authorization", "Bearer " + token).when().get("/publishers/search/name/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("jin", "jin@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("jin@mail.com"));

        PublisherDTO dtoPublisher = new PublisherDTO("Panini");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoPublisher).when().post("/publishers/insert/").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("xiaoyu", "xiaoyu@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("xiaoyu@mail.com"));

        PublisherDTO dtoPublisher = new PublisherDTO("Futabasha");

        PublisherResponseDTO publisherTest = service.insert(dtoPublisher);
        Long id = publisherTest.id();

        PublisherDTO dtoUpdate = new PublisherDTO("Ohta Publishing");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoUpdate).when().put("/publishers/update/"+id).then().statusCode(204);

        PublisherResponseDTO gen = service.findById(id);
        assertThat(gen.name(), is("Ohta Publishing"));
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("kazumi", "kazumi@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("kazumi@mail.com"));

        PublisherDTO dtoPublisher = new PublisherDTO("Shōnen Gahōsha");

        PublisherResponseDTO publisherTest = service.insert(dtoPublisher);
        Long id = publisherTest.id();

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/publishers/delete/"+id).then().statusCode(204);

    }
}