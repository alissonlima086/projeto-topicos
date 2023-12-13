package br.unitins.topicos1.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.OrderDTO;
import br.unitins.topicos1.dto.OrderResponseDTO;
import br.unitins.topicos1.service.OrderService;
import br.unitins.topicos1.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.topicos1.application.Error;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService service;

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @RolesAllowed({"User", "Admin"})
    public Response insert(OrderDTO dto) {

        String login = jwt.getSubject();
        
        try{
            LOG.info("Registrando compra");
            OrderResponseDTO retorno = service.insert(dto, login);
            LOG.info("Compra registrada");
            return Response.status(201).entity(retorno).build();
        } catch(Exception e){
            e.printStackTrace();
            LOG.error("Erro ao registrar a compra");
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    @RolesAllowed({"User", "Admin"})
    public Response findAll() {
        
        return Response.ok(service.findByAll()).build();
    }

}
