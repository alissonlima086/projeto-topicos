package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AuthorDTO;
import br.unitins.topicos1.dto.AuthorResponseDTO;
import br.unitins.topicos1.service.AuthorService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AuthorResourceTest {

    @Inject
    AuthorService service;

    @Test
    public void testFindAll(){
        given().when().get("/Authors").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        AuthorDTO dto = new AuthorDTO("Frederic Virtimann", "Emaildecontato@yahoo.com");

        AuthorResponseDTO authorTest = service.insert(dto);
        Long id = authorTest.id();

        given().when().get("/Authors/"+id).then().statusCode(200);
    }

    @Test
    public void testFindByNome(){
        AuthorDTO dto = new AuthorDTO("Jão Antonio", "maildecontato@gmail.com");

        AuthorResponseDTO authorTest = service.insert(dto);
        String name = authorTest.name();

        given().when().get("/Authors/search/nome/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        AuthorDTO dto = new AuthorDTO("Arisson Limão", "contato@orkut.com");

        given().contentType(ContentType.JSON).body(dto).when().post("/Authors").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        AuthorDTO dto = new AuthorDTO("Alexys Lebre", "ContactHere@unitins.br");

        AuthorResponseDTO authorTest = service.insert(dto);
        Long id = authorTest.id();

        AuthorDTO dtoUpdate = new AuthorDTO("Sebas Sabão", "EntreEmContato@gmail.org");

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/Authors/"+id).then().statusCode(204);

        AuthorResponseDTO gen = service.findById(id);
        assertThat(gen.name(), is("Sebas Sabão"));
        assertThat(gen.email(), is("EntreEmContato@gmail.org"));
    }

    @Test
    public void testDelete(){
        AuthorDTO dto = new AuthorDTO("Lukas Anderson", "contatocontato@rracess.com");

        AuthorResponseDTO authorTest = service.insert(dto);
        Long id = authorTest.id();

        RestAssured.given().when().delete("/Authors/"+id).then().statusCode(204);

    }
}