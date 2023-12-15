package br.unitins.topicos1.resource;

import java.io.IOException;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;
import br.unitins.topicos1.form.ComicImageForm;
import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.repository.ComicRepository;
import br.unitins.topicos1.service.ComicFileService;
import br.unitins.topicos1.service.ComicService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.topicos1.application.Error;

@Path("/comics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComicResource {

    @Inject
    ComicService service;

    @Inject
    ComicRepository repository;

    @Inject
    ComicFileService fileService;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @Path("/insert")
    @RolesAllowed({"Admin"})
    public Response insert(ComicDTO dto){
        try{
            LOG.info("Inserindo produto");
            return Response.status(Status.CREATED).entity(service.insert(dto)).build();
        } catch(Exception e){
            LOG.error("Erro ao inserir o produto");
            e.printStackTrace();
            Error error = new Error("400", "Não foi possivel concluir a ação");
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Transactional
    @Path("/update/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, ComicDTO dto){
        try{
            LOG.infof("Update em produto de id %s", id);
            service.update(id, dto);
            return Response.noContent().build();
        } catch(Exception e){
            LOG.error("Update não concluido");
            e.printStackTrace();
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @PATCH
    @Path("/upload/image/")
    @RolesAllowed({"Admin"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertImage(@MultipartForm ComicImageForm form){
        //String imageName;
        Long id = form.getId();
        try{
            LOG.infof("Inserindo imagem ao produto de id %s", id);
            String imageName = fileService.save(form.getNomeImagem(), form.getImagem());
            service.insertImage(id, imageName);
            return Response.noContent().build();
        } catch(IOException e){
            LOG.error("Erro ao inserir imagem");
            e.printStackTrace();
            Error error = new Error("409", e.getMessage());
            return Response.status(Status.CONFLICT).entity(error).build();
        }
    }

    @GET
    @Path("/download/image/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImage(@PathParam("id")Long id){

        LOG.infof("Buscando imagem do usuario de id %s", id);
        ComicResponseDTO comic = service.findById(id);
        String imageName = comic.imageName();

        ResponseBuilder response = Response.ok(fileService.getFile(imageName));
        response.header("Content-Disposition", "attachment;filename="+imageName);
        return response.build();
    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id){
        try{
            LOG.info("Deletando o produto");
            service.delete(id);
            return Response.noContent().build();
        } catch(Exception e){
            LOG.error("Deleção não concluido");
            e.printStackTrace();
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response findAll(){
        try{
            LOG.info("Buscando todos os produtos");
            return Response.ok(service.findAll()).build();
        }catch(Exception e){
            LOG.error("Produtos não encontrados");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    @Path("/search/id/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response findById(@PathParam("id") Long id){
        try{
            LOG.infof("Buscando produto de id %s", id); 
            return Response.ok(service.findById(id)).build();
        } catch(NotFoundException e){
            LOG.error("Produto não encontrado");
            e.printStackTrace();
            Error error = new Error("404", e.getMessage());
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("/search/name/{name}")
    public Response findByName(@PathParam("name") String name){

        try{
            LOG.infof("Buscando por %s", name);
            return Response.ok(service.findByName(name)).build();
        } catch(NotFoundException e){
            LOG.info("Produto não encontrado");
            Error error = new Error("404", e.getMessage());
            
            return Response.status(Status.NOT_FOUND).entity(error).build();
        }
    }
}