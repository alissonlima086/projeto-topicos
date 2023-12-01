package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.AuthorService;
import br.unitins.topicos1.service.ComicService;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.PublisherService;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class ComicResourceTest {

    @Inject
    ComicService service;
    
    @Inject
    AuthorService authorService;

    @Inject
    PublisherService publisherService;

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
        given().when().get("/comics").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        UserDTO dto = new UserDTO("luke", "luke@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("luke@mail.com"));
        
        ComicDTO dtoComic = new ComicDTO("Goburin Sureiyā", 70.0, 200, 185, 1, 1L, 1L);

        ComicResponseDTO comicTest = service.insert(dtoComic);
        Long idComic = comicTest.id();

        given().header("Authorization", "Bearer " + token).when().get("/comics/search/id/"+idComic).then().statusCode(200);
    }

    @Test
    public void testFindByName(){
        UserDTO dto = new UserDTO("juri", "juri@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("juri@mail.com"));

        ComicDTO dtoComic = new ComicDTO("DeathNote", 70.0, 200, 185, 2, 1L, 1L);

        ComicResponseDTO comicTest = service.insert(dtoComic);
        String name = comicTest.name();

        given().header("Authorization", "Bearer " + token).given().when().get("/comics/search/name/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("manon", "manon@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("manon@mail.com"));

        ComicDTO dtoComic = new ComicDTO("Konosuba", 75.0, 300, 150, 2, 1L, 2L);

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoComic).when().post("/comics/insert").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("ryu", "ryu@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("ryu@mail.com"));

        ComicDTO dtoComic = new ComicDTO("Homem-Aranha", 30.0, 250, 162, 1, 2L, 2L);
        
        ComicResponseDTO comicTest = service.insert(dtoComic);
        Long id = comicTest.id();

        ComicDTO dtoUpdate = new ComicDTO("Bleach", 60.0, 100, 103, 2, 1L, 1L);

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoUpdate).when().put("/comics/update/"+id).then().statusCode(204);

        ComicResponseDTO verify = service.findById(id);
        assertThat(verify.name(), is("Bleach"));
        assertThat(verify.price(), is(60.0));
        assertThat(verify.inventory(), is(100));
        assertThat(verify.numPages(), is(103));
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("ken", "ken@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("ken@mail.com"));

        ComicDTO dtoComic = new ComicDTO("Mushoku", 20.0, 20, 57, 1, 2L, 1L);

        ComicResponseDTO comicTest = service.insert(dtoComic);
        Long id = comicTest.id();

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/comics/delete/"+id).then().statusCode(204);
    }
}
