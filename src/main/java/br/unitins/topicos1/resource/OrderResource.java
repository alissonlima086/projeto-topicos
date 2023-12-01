package br.unitins.topicos1.resource;

import java.util.List;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.application.Error;
import br.unitins.topicos1.dto.CreditCardDTO;
import br.unitins.topicos1.dto.OrderResponseDTO;
import br.unitins.topicos1.dto.ItemOrderDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.service.OrderService;
import br.unitins.topicos1.service.ItemOrderService;
import br.unitins.topicos1.service.UserServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @Inject
    ItemOrderService itemOrderSevice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UserServiceImpl userService;

    private static final Logger LOG = Logger.getLogger(OrderResource.class);

    @GET
    @RolesAllowed({"Admin"})
    public List<OrderResponseDTO> findAll() {
        LOG.infof("Buscando todas as compras realizadas ");
        return orderService.findAll();
    }

    @GET
    @RolesAllowed({"Admin","User"})
    @Path("/historicoOrder")
    public List<OrderResponseDTO> findAllUserOrders() {
        LOG.infof("Buscando todas as compras realizadas pelo usuario");
        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);
        return orderService.findAllUsers(user.id());
    }

    @GET
    @Path("/search/id/{id}")
    @RolesAllowed({"Admin","User"})
    public OrderResponseDTO findById(@PathParam("id") Long id) {
        LOG.infof("Buscando compra especifica");
        return orderService.findById(id);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin","User"})
    @Path("/paymentpix")
    public Response buyUsingPix() {
        try{
        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        orderService.buyUsingPix(user.id());

        return Response.status(Status.CREATED).build();
        }catch(Exception e){
            LOG.error("Erro ao efetuar o pagamento com pix.", e);
            Error result = new Error(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin","User"})
    @Path("/paymentcartao")
    public Response buyUsingCreditCard(CreditCardDTO creditCardDto) {
        try{
        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        orderService.buyUsingCreditCard(user.id(),creditCardDto);

        return Response.status(Status.CREATED).build();
    }catch(Exception e){
        LOG.error("Erro ao efetuar o pagamento com cartao.", e);
        Error result = new Error(e.getMessage(), false);

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }
        
    }

    @POST
    @Path("/addItemToBuy")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin","User"})
    public Response adicionandoItem(ItemOrderDTO itemDto) {
        LOG.infof("Adicionando item ao carrinho");
        try{
        String login = jwt.getSubject();
        UserResponseDTO user = userService.findByEmail(login);
        itemOrderSevice.create(user.id(), itemDto);

        return Response.status(Status.CREATED).build();
        }catch(ConstraintViolationException e){
            Error result = new Error(e.getConstraintViolations());
            LOG.errorf("Erro ao adicionar produto no carrinho");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }catch(NullPointerException e){
            Error result = new Error(e.getMessage(), false);
            LOG.errorf("Erro ao adicionar produto no carrinho");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/delete/itemToBuy/{idItemCarrinho}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin","User"})
    public Response deleteItem(@PathParam("idItemCart") Long idItemCart) {
        LOG.infof("deletando item do carrinho");
        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        itemOrderSevice.delete(user.id(), idItemCart);

        return Response.status(Status.CREATED).build();
    }


}
