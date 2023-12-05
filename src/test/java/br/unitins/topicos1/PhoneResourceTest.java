package br.unitins.topicos1;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class PhoneResourceTest {
    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Test
    public void testGetPhone(){
        UserDTO dto = new UserDTO("fulano", "fulano5@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        Long idUser = userService.findByEmail("fulano5@mail.com").id();

        PhoneDTO phone = new PhoneDTO("44", "737373");

        
        PhoneResponseDTO phoneUser = userService.insertPhone(idUser, phone);

        String token = jwtService.generateJwt(userService.findByEmail("fulano5@mail.com"));

        given().header("Authorization", "Bearer " + token).get("/phones/phone/").then().statusCode(200);
    }

    @Test
    public void testGetPhoneEmpty(){
        UserDTO dto = new UserDTO("fulan", "fulanophone@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        String token = jwtService.generateJwt(userService.findByEmail("fulanophone@mail.com"));

        given().header("Authorization", "Bearer " + token).get("/phones/phone/").then().statusCode(404);
    }

    @Test
    public void testInsertPhone(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("fulano", "fulano6@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("fulano6@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO("63", "777777777");

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(phone).when().post("/phones/insert/phone/").then().statusCode(200);
    }

    @Test
    public void testInsertPhoneNullValue(){
        //Inserindo novo usuario
        UserDTO dto = new UserDTO("cody", "cody@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        //pegando Id do usuario
        Long idUser = userService.findByEmail("cody@mail.com").id();

        //Passando o novo phone
        PhoneDTO phone = new PhoneDTO(null, "777777777");

        //gerando token para autorização
        String token = jwtService.generateJwt(userService.findById(idUser));

        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON).body(phone).when().post("/phones/insert/phone/").then().statusCode(400);
    }
}
