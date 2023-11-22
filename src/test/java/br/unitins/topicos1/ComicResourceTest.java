package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;
import br.unitins.topicos1.service.AuthorService;
import br.unitins.topicos1.service.ComicService;
import br.unitins.topicos1.service.PublisherService;

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

    @Test
    public void testFindAll(){
        given().when().get("/comics").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        ComicDTO dto = new ComicDTO("Goburin Sureiyā", 70.0, 200, 185, 1, 1L, 1L);

        ComicResponseDTO comicTest = service.insert(dto);
        Long idComic = comicTest.id();

        given().when().get("/comics/"+idComic).then().statusCode(200);
    }

    @Test
    public void testFindByName(){
        ComicDTO dto = new ComicDTO("DeathNote", 70.0, 200, 185, 2, 1L, 1L);

        ComicResponseDTO comicTest = service.insert(dto);
        String name = comicTest.name();

        given().when().get("/comics/search/nome/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        ComicDTO dto = new ComicDTO("Konosuba", 75.0, 300, 150, 2, 1L, 2L);

        given().contentType(ContentType.JSON).body(dto).when().post("/comics").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        ComicDTO dto = new ComicDTO("Homem-Aranha", 30.0, 250, 162, 1, 2L, 2L);
        
        ComicResponseDTO comicTest = service.insert(dto);
        Long id = comicTest.id();

        ComicDTO dtoUpdate = new ComicDTO("Bleach", 60.0, 100, 103, 2, 1L, 1L);

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/comics/"+id).then().statusCode(204);

        ComicResponseDTO verify = service.findById(id);
        assertThat(verify.name(), is("Bleach"));
        assertThat(verify.price(), is(60.0));
        assertThat(verify.inventory(), is(100));
        assertThat(verify.numPages(), is(103));
    }

    @Test
    public void testDelete(){
        ComicDTO dto = new ComicDTO("Mushoku", 20.0, 20, 57, 1, 2L, 1L);

        ComicResponseDTO comicTest = service.insert(dto);
        Long id = comicTest.id();

        RestAssured.given().when().delete("/comics/"+id).then().statusCode(204);
    }
}
