package br.unitins.topicos1.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.ItemOrderResponseDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.service.ItemOrderService;
import br.unitins.topicos1.service.UserServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/itemOrder")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemOrderResource {

    @Inject
    ItemOrderService itemOrderSevice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UserServiceImpl userService;

    private static final Logger LOG = Logger.getLogger(OrderResource.class);
    
    @GET
    @RolesAllowed({"Admin"})
    public List<ItemOrderResponseDTO> findAll() {
        LOG.infof("Buscando todos Itens adicionados nos carrinhos ");
        return itemOrderSevice.findAll();
    }

    @GET
    @Path("/itensoncart")
    @RolesAllowed({"Admin","User"})
    public List<ItemOrderResponseDTO> findAllCarrinho() {
        LOG.infof("Buscando itens do carrinho do usuario ");
        String login = jwt.getSubject();

        UserResponseDTO usuario = userService.findByEmail(login);
        return itemOrderSevice.findAllCart(usuario.id());
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public ItemOrderResponseDTO findById(@PathParam("id") Long id) {
        LOG.infof("Buscando itens especifico adicionado nos carrinhos ");
        return itemOrderSevice.findById(id);
    }

    @GET
    @Path("/quantityincart")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin","User"})
    public long count() {
        LOG.infof("Buscando quantidade de itens no carrinho ");

        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        return itemOrderSevice.countItemOrder(user.id());
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin"})
    public long count2(){
        LOG.infof("Buscando quantidade de itens dos carrinhos ");
        return itemOrderSevice.count();
    }

}