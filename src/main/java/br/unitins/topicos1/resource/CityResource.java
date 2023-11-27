package br.unitins.topicos1.resource;

import br.unitins.topicos1.service.CityService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityResource {

    @Inject
    CityService service;

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
    @Path("/search/nome/{name}")
    public Response findByName(@PathParam("name") String name){
        return Response.ok(service.findByName(name)).build();
    }
    
}
