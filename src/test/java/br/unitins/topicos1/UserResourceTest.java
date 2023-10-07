package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.model.Phone;
import br.unitins.topicos1.service.UserService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class UserResourceTest {

    @Inject
    UserService service;

    @Test
    public void testFindAll(){
        given().when().get("/users").then().statusCode(200);
    }

    @Test
    public void testInsert(){
        UserDTO dto = new UserDTO("marquinho", "marcos@mail.com", "123456");

        given().contentType(ContentType.JSON).body(dto).when().post("/users").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        UserDTO dto = new UserDTO("marquinho", "marcos@mail.com", "123456");
        
        UserResponseDTO userTest = service.insert(dto);
        Long id = userTest.id();

        UserDTO dtoUpdate = new UserDTO("marcos", "marcos.m@mail.com", "123456");

        given().contentType(ContentType.JSON).body(dtoUpdate).when().put("/users/"+id).then().statusCode(204);

        UserResponseDTO verify = service.findById(id);
        assertThat(verify.username(), is("marcos"));
        assertThat(verify.email(), is("marcos.m@mail.com"));
    }

    @Test
    public void testDelete(){
        UserDTO dto = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(dto);
        Long id = userTest.id();

        RestAssured.given().when().delete("/users/"+id).then().statusCode(204);
    }

    
    
    @Test
    public void testInsertPhone(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");
        
        UserResponseDTO userTest = service.insert(userDTO);
        Long id = userTest.id();

        PhoneDTO dto = new PhoneDTO("63", "96666-6666");
        given().contentType(ContentType.JSON).body(dto).when().post("/users/phone/insert/"+id).then().statusCode(201);

    }
    
    @Test
    public void testUpdatePhone(){
        UserDTO userDTO = new UserDTO("marquinho", "marcos@mail.com", "123456");

        UserResponseDTO userTest = service.insert(userDTO);
        Long idUser = userTest.id();

        PhoneDTO dto = new PhoneDTO("63", "96666-6666");

        PhoneResponseDTO phoneTest = service.insertPhone(idUser, dto);
        Long idPhone = phoneTest.id();

        PhoneDTO phoneUpdate = new PhoneDTO("63", "99999-9999");

        given().contentType(ContentType.JSON).body(phoneUpdate).when().put("/users/phone/update/"+idPhone).then().statusCode(204);
    }


    
}
