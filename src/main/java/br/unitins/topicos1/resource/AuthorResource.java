package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.AuthorDTO;
import br.unitins.topicos1.service.AuthorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Error;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @Inject
    AuthorService service;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @RolesAllowed({"Admin"})
    @Path("/insert/")
    public Response insert(AuthorDTO dto){
        try{
            LOG.info("Inserindo autor");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch(Exception e){
            LOG.error("Erro ao inserir autor");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, AuthorDTO dto){
        try{
            LOG.info("Atualizando os dados do autor");
            service.update(id, dto);
            return Response.noContent().build();
        } catch(NotFoundException e){
            LOG.error("Erro ao atualizar dados do autor");
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id){
        try{
            LOG.infof("Deletando autor de id %s", id);
            service.delete(id);
            return Response.noContent().build();
        } catch(NotFoundException e){
            LOG.error("Erro ao deletar autor");
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response findAll(){
        try{
            LOG.info("Buscando autores");
            return Response.ok(service.findAll()).build();
        } catch(NotFoundException e){
            LOG.error("Erro ao buscar por autores");
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({"Admin"})
    public Response findById(@PathParam("id") Long id){
        try{
            LOG.infof("Buscando por autor de id %s", id);
            return Response.ok(service.findById(id)).build();
        } catch(NotFoundException e){
            LOG.error("Erro ao buscar por autor");
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("/search/name/{AuthorName}")
    public Response findByAuthorName(@PathParam("AuthorName") String authorName){
        try{
            LOG.infof("Buscando por %s", authorName);
            return Response.ok(service.findByName(authorName)).build();
        } catch(NotFoundException e){
            LOG.error("Erro ao buscar por autor");
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }
    
}
