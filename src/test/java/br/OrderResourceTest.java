package br;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;
import br.unitins.topicos1.dto.CompleteUserDTO;
import br.unitins.topicos1.dto.ItemOrderDTO;
import br.unitins.topicos1.dto.OrderDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.model.ItemOrder;
import br.unitins.topicos1.repository.UserRepository;
import br.unitins.topicos1.service.ComicService;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.OrderService;
import br.unitins.topicos1.service.UserService;


@QuarkusTest
public class OrderResourceTest {

    @Inject
    UserService userService;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    ComicService comicService;

    @Inject
    OrderService orderService;

    @Test
    public void testFindAllOrders(){

        UserDTO dto = new UserDTO("joaun", "jaounnnn@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        ComicDTO dtoComic = new ComicDTO("genericComic", 70.0, 200, 185, 1, 1L, 1L);
        ComicResponseDTO comicTest = comicService.insert(dtoComic);
        Long idComic = comicTest.id();

        CompleteUserDTO completeUser = new CompleteUserDTO("Joao Medeiros", "55577788801", 2);

        userService.completeUser(userTest.id(), completeUser);

        String token = jwtService.generateJwt(userService.findByEmail("jaounnnn@mail.com"));

        ItemOrderDTO itemOne = new ItemOrderDTO(5, idComic);
        List<ItemOrderDTO> listItem = new ArrayList<ItemOrderDTO>();
        listItem.add(itemOne);
        OrderDTO orderDTO = new OrderDTO(listItem);

        orderService.insert(orderDTO, userTest.email());

        given().header("Authorization", "Bearer " + token).when().get("/order/").then().statusCode(200);
    }

    @Test
    public void testInsertOrder(){
        UserDTO dto = new UserDTO("Maryyy", "marimi@mail.com", hashService.getHashPassword("12345"), 2);
        UserResponseDTO userTest = userService.insert(dto);

        ComicDTO dtoComic = new ComicDTO("generico", 70.0, 200, 185, 1, 1L, 1L);
        ComicResponseDTO comicTest = comicService.insert(dtoComic);
        Long idComic = comicTest.id();

        CompleteUserDTO completeUser = new CompleteUserDTO("Mary Fulana", "987654322", 1);

        userService.completeUser(userTest.id(), completeUser);

        String token = jwtService.generateJwt(userService.findByEmail("marimi@mail.com"));

        ItemOrderDTO itemOne = new ItemOrderDTO(5, idComic);
        List<ItemOrderDTO> listItem = new ArrayList<ItemOrderDTO>();
        listItem.add(itemOne);
        OrderDTO orderDTO = new OrderDTO(listItem);

        given().header("Authorization", "Bearer " + token).when().contentType(ContentType.JSON).body(orderDTO).post("/order/buy/").then().statusCode(201);
    }
    
}
