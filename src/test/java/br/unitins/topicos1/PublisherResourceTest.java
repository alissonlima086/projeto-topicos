package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.PublisherDTO;
import br.unitins.topicos1.dto.PublisherResponseDTO;
import br.unitins.topicos1.service.PublisherService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class PublisherResourceTest {

    @Inject
    PublisherService service;

    @Test
    public void testFindAll(){
        given().when().get("/Publishers").then().statusCode(200);
    }

    @Test
    public void testFindById(){
        PublisherDTO dto = new PublisherDTO("Marvel");

        PublisherResponseDTO publisherTest = service.insert(dto);
        Long id = publisherTest.id();

        given().when().get("/Publishers/"+id).then().statusCode(200);
    }

    @Test
    public void testFindByNome(){
        PublisherDTO dto = new PublisherDTO("DC");

        PublisherResponseDTO publisherTest = service.insert(dto);
        String name = publisherTest.name();

        given().when().get("/Publishers/search/nome/"+name).then().statusCode(200);
    }

    @Test
    public void testInsert(){
        PublisherDTO dto = new PublisherDTO("Square Enix");

        given().contentType(ContentType.JSON).body(dto).when().post("/Publishers").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        PublisherDTO dto = new PublisherDTO("Futabasha");

        PublisherResponseDTO publisherTest = service.insert(dto);
        Long id = publisherTest.id();

        PublisherDTO dtoUpdate = new PublisherDTO("Ohta Publishing");

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/Publishers/"+id).then().statusCode(204);

        PublisherResponseDTO gen = service.findById(id);
        assertThat(gen.name(), is("Ohta Publishing"));
    }

    @Test
    public void testDelete(){
        PublisherDTO dto = new PublisherDTO("Shōnen Gahōsha");

        PublisherResponseDTO publisherTest = service.insert(dto);
        Long id = publisherTest.id();

        RestAssured.given().when().delete("/Publishers/"+id).then().statusCode(204);

    }
}