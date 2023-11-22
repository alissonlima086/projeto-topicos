package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.service.AddressService;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AddressResourceTest {

    @Inject
    AddressService service;

    @Inject
    UserService userService;

    @Test
    public void testFindAll(){
        given().when().get("/addresses").then().statusCode(200);
    }
    
    // @Test
    // public void testInsert(){
    //     StateDTO stateDTO = new StateDTO("Tocantins", "TO");

    //     StateResponseDTO stateTest = 

    //     CityDTO = new CityDTO("Palmas", null);

    //     UserDTO dto = new UserDTO("marquinho", "marcos@mail.com", "123456");
    //     UserResponseDTO userTest = userService.insert(dto);
    //     Long idUser = userTest.id();



    //     AddressDTO addressDTO = new AddressDTO("Casa", "4444444144", "Rua tal avenida tal", "Ao lado da", 1L, idUser);
    // }
}
