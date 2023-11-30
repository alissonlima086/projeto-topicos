package br.unitins.topicos1.resource;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.GenreDTO;
import br.unitins.topicos1.service.GenreService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

@Path("/genres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResource {

    @Inject
    GenreService service;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @Path("/insert/")
    @RolesAllowed({"Admin"})
    public Response insert(GenreDTO dto){
        try{
            LOG.infof("Inserindo genero %s", dto.name());
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch(Exception e){
            LOG.info("Erro ao inserir o genero");
            e.printStackTrace();
            Error error = new Error("400");
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, GenreDTO dto){
        try{
            LOG.info("Atualizando os dados de genero");
            service.update(id, dto);
            return Response.noContent().build();
        } catch (Exception e){
            LOG.info("Erro ao atualizar o genero");
            e.printStackTrace();
            Error error = new Error("404");
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id){
        try{
            service.delete(id);
            return Response.noContent().build();
        } catch (Exception e){
            LOG.info("Erro ao deletar o genero");
            e.printStackTrace();
            Error error = new Error("404");
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @RolesAllowed({"User", "Admin"})
    public Response findAll(){
        try{
            LOG.info("Buscando todos os generos");
            return Response.ok(service.findAll()).build();
        } catch (Exception e){
            LOG.info("Erro ao buscar os generos");
            e.printStackTrace();
            Error error = new Error("400");
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({"Admin"})
    public Response findById(@PathParam("id") Long id){
        try{
            LOG.infof("Buscando genero de id %s", id);
            return Response.ok(service.findById(id)).build();
        } catch(Exception e){
            LOG.info("Erro ao encontrar o genero");
            e.printStackTrace();
            Error error = new Error("400");
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    @Path("/search/name/{name}")
    @RolesAllowed({"Admin", "User"})
    public Response findByName(@PathParam("name") String name){
        try{
            LOG.infof("Buscando pelo genero %s", name);
            return Response.ok(service.findByName(name)).build();
        } catch(Exception e){
            LOG.info("Erro ao encontrar o genero");
            e.printStackTrace();
            Error error = new Error("400");
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    
}
