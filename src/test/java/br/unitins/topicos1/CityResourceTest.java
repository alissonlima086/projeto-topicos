package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.CityDTO;
import br.unitins.topicos1.dto.CityResponseDTO;
import br.unitins.topicos1.dto.GenreDTO;
import br.unitins.topicos1.dto.GenreResponseDTO;
import br.unitins.topicos1.model.City;
import br.unitins.topicos1.service.CityService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class CityResourceTest {

    @Inject
    CityService service;
    
    @Test
    public void testFindAll(){
        given().when().get("/cities").then().statusCode(200);
    }

    /*
    @Test
    public void testFindByName(){
        CityDTO city = new CityDTO("Cidade", 1);

        CityResponseDTO cityTest = service.insert(dto);
        
        String name = cityTest.name();

        given().when().get("/genres/search/nome/"+name).then().statusCode(200);
    }
     */

}
