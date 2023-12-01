package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.GenreDTO;
import br.unitins.topicos1.dto.GenreResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.resource.AuthResource;
import br.unitins.topicos1.service.GenreService;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.logging.Logger;

@QuarkusTest
public class GenreResourceTest {

    @Inject
    GenreService genreService;

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;
    
    @Test
    public void testFindAllNotLogged(){
        given().when().get("/genres").then().statusCode(401);
    }

    @Test
    public void testFindAll(){
        UserDTO dto = new UserDTO("ino", "ino@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("ino@mail.com"));
        
        given().header("Authorization", "Bearer " + token).when().get("/genres").then().statusCode(200);
    }

    
    @Test
    public void testFindById(){
        UserDTO dto = new UserDTO("baiken", "baiken@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("baiken@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Sci-Fi");

        GenreResponseDTO genreTest = genreService.insert(genreDTO);
        Long id = genreTest.id();

        given().header("Authorization", "Bearer " + token).when().get("/genres/search/"+id).then().statusCode(200);
    }

    @Test
    public void testFindByName(){
        UserDTO dto = new UserDTO("ky", "ky@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("ky@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Adventure");

        GenreResponseDTO genreTest = genreService.insert(genreDTO);
        String name = genreTest.name();

        given().header("Authorization", "Bearer " + token).when().get("/genres/search/name/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("chipp", "chipp@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("chipp@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Romance");

        given().header("Authorization", "Bearer " + token).given().contentType(ContentType.JSON).body(genreDTO).when().post("/genres/insert/").then().statusCode(201);
    }

    

    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("giovanna", "giovanna@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("giovanna@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Dtama");
        GenreResponseDTO insertGenre = genreService.insert(genreDTO);
        Long id = insertGenre.id();

        GenreDTO updateGenre = new GenreDTO("Drama");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(updateGenre).when().put("/genres/update/"+id).then().statusCode(204);

        GenreResponseDTO gen = genreService.findById(id);
        assertThat(gen.name(), is("Drama"));
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("faust", "faust@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("faust@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Conto");
        GenreResponseDTO insertGenre = genreService.insert(genreDTO);
        Long id = insertGenre.id();

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/genres/delete/"+id).then().statusCode(204);
    }

    // ---------- Logado como usuario ----------

    @Test
    public void testFindByIdAsUser(){
        UserDTO dto = new UserDTO("elphelt", "elphelt@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("elphelt@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Horror");

        GenreResponseDTO genreTest = genreService.insert(genreDTO);
        Long id = genreTest.id();

        given().header("Authorization", "Bearer " + token).when().get("/genres/search/"+id).then().statusCode(403);
    }

    @Test
    public void testInsertAsUser(){
        UserDTO dto = new UserDTO("potemkin", "potemkin@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("potemkin@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Fantasia");

        given().header("Authorization", "Bearer " + token).given().contentType(ContentType.JSON).body(genreDTO).when().post("/genres/insert/").then().statusCode(403);
    }

    @Test
    public void testUpdateAsUser(){
        UserDTO dto = new UserDTO("valentina", "valentina@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("valentina@mail.com"));

        GenreDTO genreDTO = new GenreDTO("fabla");
        GenreResponseDTO insertGenre = genreService.insert(genreDTO);
        Long id = insertGenre.id();

        GenreDTO updateGenre = new GenreDTO("Fabula");

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(updateGenre).when().put("/genres/update/"+id).then().statusCode(403);

        GenreResponseDTO gen = genreService.findById(id);
        assertThat(gen.name(), is("fabla"));
    }

    @Test
    public void testDeleteAsUser(){
        UserDTO dto = new UserDTO("sin", "sin@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("sin@mail.com"));

        GenreDTO genreDTO = new GenreDTO("Conto");
        GenreResponseDTO insertGenre = genreService.insert(genreDTO);
        Long id = insertGenre.id();

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/genres/delete/"+id).then().statusCode(403);
    }
}
