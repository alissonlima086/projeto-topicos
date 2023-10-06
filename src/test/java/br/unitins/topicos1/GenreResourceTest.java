package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.GenreDTO;
import br.unitins.topicos1.dto.GenreResponseDTO;
import br.unitins.topicos1.service.GenreService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class GenreResourceTest {

    @Inject
    GenreService service;

    @Test
    public void testFindAll(){
        given().when().get("/genres").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        GenreDTO dto = new GenreDTO("Sci-Fi");

        GenreResponseDTO genreTest = service.insert(dto);
        Long id = genreTest.id();

        given().when().get("/genres/"+id).then().statusCode(200);
    }

    @Test
    public void testFindByNome(){
        GenreDTO dto = new GenreDTO("Romance");

        GenreResponseDTO genreTest = service.insert(dto);
        String name = genreTest.name();

        given().when().get("/genres/search/nome/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        GenreDTO dto = new GenreDTO("Sci-Fi");

        given().contentType(ContentType.JSON).body(dto).when().post("/genres").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        GenreDTO dto = new GenreDTO("Sci-Fi");

        GenreResponseDTO genreTest = service.insert(dto);
        Long id = genreTest.id();

        GenreDTO dtoUpdate = new GenreDTO("Ficção Cientifica");

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/genres/"+id).then().statusCode(204);

        GenreResponseDTO gen = service.findById(id);
        assertThat(gen.name(), is("Ficção Cientifica"));
    }

    @Test
    public void testDelete(){
        GenreDTO dto = new GenreDTO("Sci-Fi");

        GenreResponseDTO genreTest = service.insert(dto);
        Long id = genreTest.id();

        RestAssured.given().when().delete("/genres/"+id).then().statusCode(204);

    }
    


}
