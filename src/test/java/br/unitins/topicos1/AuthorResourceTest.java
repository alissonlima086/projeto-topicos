package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AuthorDTO;
import br.unitins.topicos1.dto.AuthorResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.AuthorService;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AuthorResourceTest {

    @Inject
    AuthorService service;

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
        given().when().get("/authors").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        UserDTO dto = new UserDTO("jolyne", "jolyne@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("jolyne@mail.com"));

        AuthorDTO dtoAuthor = new AuthorDTO("Frederic Virtimann", "Emaildecontato@yahoo.com");

        AuthorResponseDTO authorTest = service.insert(dtoAuthor);
        Long id = authorTest.id();

        given().header("Authorization", "Bearer " + token).when().get("/authors/search/"+id).then().statusCode(200);
    }

    @Test
    public void testFindByNome(){
        UserDTO dto = new UserDTO("jotaro", "jotaro@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("jotaro@mail.com"));

        AuthorDTO dtoAuthor = new AuthorDTO("Jão Antonio", "maildecontato@gmail.com");

        AuthorResponseDTO authorTest = service.insert(dtoAuthor);
        String name = authorTest.name();

        given().header("Authorization", "Bearer " + token).when().get("/authors/search/name/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("joseph", "joseph@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("joseph@mail.com"));

        AuthorDTO dtoAuthor = new AuthorDTO("Arisson Limão", "contato@orkut.com");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoAuthor).when().post("/authors/insert/").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("josuke", "josuke@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("josuke@mail.com"));

        AuthorDTO dtoAuthor = new AuthorDTO("Alexys Lebre", "ContactHere@unitins.br");

        AuthorResponseDTO authorTest = service.insert(dtoAuthor);
        Long id = authorTest.id();

        AuthorDTO dtoUpdate = new AuthorDTO("Sebas Sabão", "EntreEmContato@gmail.org");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoUpdate).when().put("/authors/update/"+id).then().statusCode(204);

        AuthorResponseDTO gen = service.findById(id);
        assertThat(gen.name(), is("Sebas Sabão"));
        assertThat(gen.email(), is("EntreEmContato@gmail.org"));
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("ff", "ff@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("ff@mail.com"));
        
        AuthorDTO dtoAuthor = new AuthorDTO("Lukas Anderson", "contatocontato@rracess.com");

        AuthorResponseDTO authorTest = service.insert(dtoAuthor);
        Long id = authorTest.id();

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/authors/delete/"+id).then().statusCode(204);

    }
}