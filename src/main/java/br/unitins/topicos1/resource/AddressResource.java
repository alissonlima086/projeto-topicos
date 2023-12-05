package br.unitins.topicos1.resource;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.AddressDTO;
import br.unitins.topicos1.repository.AddressRepository;
import br.unitins.topicos1.service.AddressService;
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
import br.unitins.topicos1.validation.ValidationException;
import br.unitins.topicos1.application.Error;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

    @Inject
    AddressService service;

    @Inject
    AddressRepository repository;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    public Response insert(AddressDTO dto){
        try{
            LOG.info("Inserindo endereço");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch(ValidationException e){
            LOG.error("endereço não inserido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update/{id}")
    public Response update(@PathParam("id") Long id, AddressDTO dto){
        try{
            LOG.infof("Update em endereço de id %s", id);
            service.update(id, dto);
            return Response.noContent().build();
        } catch(NotFoundException e){
            LOG .error("Endereço não encontrado");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        } catch(ValidationException e){
            LOG .error("Update não concluido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        try{
            LOG.infof("Deletando endereço de id %s", id);
            service.delete(id);
            return Response.noContent().build();
        } catch(NotFoundException e){
            LOG .error("Endereço não encontrado");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    public Response findAll(){
        try{
            LOG.info("Buscando todos os endereços");
            return Response.ok(service.findAll()).build();
        } catch(NotFoundException e){
            LOG .error("Endereços não encontrados");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    @Path("/search/city/{cityId}")
    public Response findByCity(@PathParam("cityId")Long cityId){
        try{
            LOG.infof("Buscando endereços da cidade de id %s", cityId);
            return Response.ok(service.findByCity(cityId)).build();
        } catch(NotFoundException e){
            LOG .error("Endereços não encontrados");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }
    
}
