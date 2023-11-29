package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.topicos1.application.Error;
import br.unitins.topicos1.dto.PhysicalPersonDTO;
import br.unitins.topicos1.dto.PhysicalPersonResponseDTO;
import br.unitins.topicos1.service.PhysicalPersonService;

@Path("/physicalperson")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhysicalPersonResource {

    @Inject
    PhysicalPersonService physicalPersonService;

    private static final Logger LOG = Logger.getLogger(CityResource.class);

    @GET
    @RolesAllowed({"Admin"})
    public List<PhysicalPersonResponseDTO> findAll() {
        LOG.infof("Buscando todas Pessoas Fisicas registradas");
        return physicalPersonService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public PhysicalPersonResponseDTO findById(@PathParam("id") Long id) {
        LOG.infof("Buscando Pessoa Fisica pelo id");
        return physicalPersonService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(PhysicalPersonDTO dto) {
        try {
            PhysicalPersonResponseDTO physicalPerson = physicalPersonService.create(dto);
            LOG.infof("Pessoa fisica criada com sucesso");
            return Response.status(Status.CREATED).entity(physicalPerson).build();
        } catch (ConstraintViolationException e) {
            Error result = new Error(e.getConstraintViolations());
            LOG.errorf("Erro ao criar pessoa fisica");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, PhysicalPersonDTO dto) {
        try {
            PhysicalPersonResponseDTO physicalPerson = physicalPersonService.update(id, dto);
            physicalPersonService.update(id, dto);
            LOG.infof("Sucesso ao atualizar os dados de pessoa fisica");
            return Response.ok(physicalPerson).status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            Error result = new Error(e.getConstraintViolations());
            LOG.errorf("Erro ao atualizar os dados de pessoa fisica");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        physicalPersonService.delete(id);
        LOG.infof("Pessoa Fisica deletada com sucesso");
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin"})
    public long count() {
        LOG.infof("Buscando quantidade d pessoas fisicas registradas");
        return physicalPersonService.count();
    }

    @GET
    @Path("/search/{email}")
    @RolesAllowed({"Admin"})
    public List<PhysicalPersonResponseDTO> search(@PathParam("email") String name) {
        LOG.infof("Buscando pessoa fisica pelo email");
        return physicalPersonService.findByName(name);

    }
}
