package br.unitins.topicos1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AddressDTO;
import br.unitins.topicos1.dto.AddressResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.AddressService;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
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

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Test
    public void testFindAll(){
        UserDTO dtoUser = new UserDTO("drew", "drew@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dtoUser);

        String token = jwtService.generateJwt(userService.findByEmail("drew@mail.com"));
        Long idUser = userService.findByEmail("drew@mail.com").id();

        AddressDTO dtoAddress = new AddressDTO("casa", "222222", "rua x", "frente ao bar", 1L);

        AddressResponseDTO addressTest = service.insert(idUser, dtoAddress);

        given().header("Authorization", "Bearer " + token).when().get("/addresses").then().statusCode(200);
    }

    /*
    @Test
    public void testFindByCity(){
        UserDTO dtoUser = new UserDTO("kairi", "kairi@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dtoUser);

        String token = jwtService.generateJwt(userService.findByEmail("kairi@mail.com"));
        Long idUser = userService.findByEmail("kairi@mail.com").id();

        AddressDTO dtoAddress = new AddressDTO("trabalho", "222222", "alameda t", "ao lado do shopping", 1L);

        AddressResponseDTO addressTest = service.insert(idUser, dtoAddress);

        Integer idCity = 1;

        given().header("Authorization", "Bearer " + token).when().get("/addresses/search/city/"+ idCity).then().statusCode(200);
    }

    @Test
    public void testFindByCityWithoutAddress(){
        UserDTO dtoUser = new UserDTO("niki", "nikki@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dtoUser);

        String token = jwtService.generateJwt(userService.findByEmail("nikki@mail.com"));
        Long idUser = userService.findByEmail("nikki@mail.com").id();

        AddressDTO dtoAddress = new AddressDTO("faculdade", "222222", "alameda e", "ao lado do shopping", 1L);

        AddressResponseDTO addressTest = service.insert(idUser, dtoAddress);

        Long idCity = 2L;

        given().header("Authorization", "Bearer " + token).when().get("/addresses/search/city/"+ idCity).then().statusCode(400);
    }
     */
    
    @Test
    public void testInsert(){
        UserDTO dtoUser = new UserDTO("rhea", "rhea@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dtoUser);

        String token = jwtService.generateJwt(userService.findByEmail("rhea@mail.com"));

        AddressDTO dtoAddress = new AddressDTO("Trabalho", "222222", "rua dos bobos", "numero 0", 2L);

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoAddress).when().post("/addresses/insert/").then().statusCode(201);
    }

    @Test
    public void testUpdate(){
        UserDTO dtoUser = new UserDTO("finn", "finn@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dtoUser);

        String token = jwtService.generateJwt(userService.findByEmail("finn@mail.com"));
        Long idUser = userService.findByEmail("finn@mail.com").id();

        AddressDTO dtoAddress = new AddressDTO("Porao", "222222", "Rua x", "numero y", 2L);
        AddressResponseDTO addressTest = service.insert(idUser, dtoAddress);

        Long id = addressTest.id();

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(dtoAddress).when().put("/addresses/update/"+ id).then().statusCode(204);
    }


    @Test
    public void testDelete(){
        UserDTO dtoUser = new UserDTO("iyo", "iyo@mail.com", hashService.getHashPassword("12345"), 1);
        UserResponseDTO userTest = userService.insert(dtoUser);

        String token = jwtService.generateJwt(userService.findByEmail("iyo@mail.com"));
        Long idUser = userService.findByEmail("iyo@mail.com").id();

        AddressDTO dtoAddress = new AddressDTO("castelo", "222222", "Rua fermento", "numero 4", 1L);
        AddressResponseDTO addressTest = service.insert(idUser, dtoAddress);

        Long id = addressTest.id();

        RestAssured.given().header("Authorization", "Bearer " + token).when().delete("/addresses/delete/"+id).then().statusCode(204);
    }
}
