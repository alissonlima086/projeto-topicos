package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.service.UserService;
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

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;

    @POST
    public Response insert(UserDTO dto){
        return Response.status(Status.CREATED).entity(service.insert(dto)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UserDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Transactional
    @Path("/phone/insert/{id}")
    public Response insertPhone(@PathParam("id") Long id, PhoneDTO dto){
        return Response.status(Status.CREATED).entity(service.insertPhone(id, dto)).build();
    }


    @PUT
    @Transactional
    @Path("/phone/update/{id}")
    public Response updatePhone(@PathParam("id") Long id, PhoneDTO dto){
        service.updatePhone(id, dto);
        return Response.noContent().build();
    }

    @GET
    @Path("/phone")
    public Response findAllPhones(){
        return Response.ok(service.findAllPhones()).build();
    }

    @GET
    @Path("/phone/{id}")
    public Response findPhoneByUserId(@PathParam("id") Long id){
        return Response.ok(service.findPhoneByUserId(id)).build();
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
    @Path("/search/name/{username}")
    public Response findByUsername(@PathParam("username") String username){
        return Response.ok(service.findByUsername(username)).build();
    }
    
}
