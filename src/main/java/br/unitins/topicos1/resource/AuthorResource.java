package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.AuthorDTO;
import br.unitins.topicos1.service.AuthorService;
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

@Path("/Authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @Inject
    AuthorService service;

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(AuthorDTO dto){
        return Response.status(Status.CREATED).entity(service.insert(dto)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, AuthorDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response findAll(){
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{AuthorName}")
    public Response findByAuthorName(@PathParam("AuthorName") String authorName){
        return Response.ok(service.findByName(authorName)).build();
    }
    
}
