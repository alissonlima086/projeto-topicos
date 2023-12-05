package br.unitins.topicos1.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.repository.PhoneRepository;
import br.unitins.topicos1.service.UserService;
import br.unitins.topicos1.validation.ValidationException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import br.unitins.topicos1.validation.ValidationException;

import br.unitins.topicos1.application.Error;

@Path("/phones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhoneResource {
    @Inject
    JsonWebToken jwt;

    @Inject
    UserService userService;

    @Inject
    PhoneRepository phoneRepository;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    // ---------- phones ----------

    @GET
    @Path("/phone/")
    @RolesAllowed({"User", "Admin"})
    public Response getPhone(){

        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        Long id = user.id();

        try{
            LOG.info("Buscando telefones");
            List<PhoneResponseDTO> phoneDTO = userService.findPhoneByUserId(id);
            return Response.ok(phoneDTO).build();
        } catch (NotFoundException e){
            LOG.error("Telefones não encontrados");
            e.printStackTrace();
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }

    }

    @POST
    @Path("/insert/phone/")
    @RolesAllowed({"User", "Admin"})
    public Response insertPhone(PhoneDTO phone){

        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        Long id = user.id();

        try{
            LOG.info("Inserindo telefone");
            PhoneResponseDTO phoneDTO = userService.insertPhone(id, phone);
            return Response.ok(phoneDTO).build();
        } catch(ValidationException e){
            LOG.error("Telefone não inserido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("/update/phone/{id}")
    @RolesAllowed({"User", "Admin"})
    public Response updatePhone(@PathParam("id") Long id, PhoneDTO phone){

        try{
            LOG.infof("IUpdate em telefone de id %s", id);
            PhoneResponseDTO phoneDTO = userService.updatePhone(id, phone);
            return Response.noContent().build();
        } catch(NotFoundException e){
            LOG.error("Update não concluido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        } catch(ValidationException e){
            LOG.error("Erro de validação");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("/delete/phone/{id}")
    @RolesAllowed({"User", "Admin"})
    public Response deletePhone(@PathParam("id") Long id, PhoneDTO phone){
        try{
            LOG.info("Deletando phone");
            userService.deletePhone(id);
            return Response.noContent().build();
        } catch(NotFoundException e){
            LOG.error("Phone não encontrado");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }
}
