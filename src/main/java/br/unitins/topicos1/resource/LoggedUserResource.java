package br.unitins.topicos1.resource;

import java.io.IOException;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.topicos1.service.UserFileService;
import br.unitins.topicos1.service.UserService;
import br.unitins.topicos1.application.Error;
import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UpdatePasswordDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.form.UserImageForm;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.PhoneRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuariologado")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoggedUserResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UserService userService;

    @Inject
    UserFileService fileService;

    @Inject
    PhoneRepository phoneRepository;

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response getUsuario() {

        // obtendo o login pelo token jwt
        String login = jwt.getSubject();

        return Response.ok(userService.findByEmail(login)).build();

    }




    // ---------- Image ----------

    @PATCH
    @Path("/upload/image")
    @RolesAllowed({ "User", "Admin" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response saveimage(@MultipartForm UserImageForm form){
        String imageName;
        try {
            imageName = fileService.save(form.getNomeImagem(), form.getImagem());
        } catch (IOException e) {
            e.printStackTrace();
            Error error = new Error("409", e.getMessage());
            return Response.status(Status.CONFLICT).entity(error).build();
        }

        String login = jwt.getSubject();
        UserResponseDTO userDTO = userService.findByEmail(login);
        userDTO = userService.updateImageName(userDTO.id(), imageName);

        return Response.ok(userDTO).build();

    }

    @GET
    @Path("/download/imagem/{imageName}")
    @RolesAllowed({ "User", "Admin" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("imageName") String imageName) {
        ResponseBuilder response = Response.ok(fileService.getFile(imageName));
        response.header("Content-Disposition", "attachment;filename="+imageName);
        return response.build();
    }

    // ---------- phones ----------

    @PATCH
    @Path("/insert/phone")
    @RolesAllowed({"User", "Admin"})
    public Response insertPhone(PhoneDTO phone){

        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        Long id = user.id();

        try{
            PhoneResponseDTO phoneDTO = userService.insertPhone(id, phone);
            return Response.ok(phoneDTO).build();
        } catch(Exception e){
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PATCH
    @Path("/update/phone/{id}")
    @RolesAllowed({"User", "Admin"})
    public Response updatePhone(@PathParam("id") Long id, PhoneDTO phone){
        
        String login = jwt.getSubject();

        try{
            PhoneResponseDTO phoneDTO = userService.updatePhone(id, phone);
            return Response.ok(phoneDTO).build();
        } catch(Exception e){
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }


    }


    // ---------- Updates ----------

    @PATCH
    @Path("/update/email")
    @RolesAllowed({"User", "Admin"})
    public Response updateEmail(String newEmail){

        String login = jwt.getSubject();

        try{
            UserResponseDTO userDTO = userService.updateEmail(login, newEmail);
            return Response.ok(userDTO).build();
        } catch(Exception e){
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PATCH
    @Path("/update/username")
    @RolesAllowed({"User", "Admin"})
    public Response updateUsername(String newUsername){

        String login = jwt.getSubject();

        try{
            UserResponseDTO userDTO = userService.updateUsername(login, newUsername);
            return Response.ok(userDTO).build();
        } catch(Exception e){
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PATCH
    @Path("/update/password")
    @RolesAllowed({"User", "Admin"})
    public Response updatePassword(UpdatePasswordDTO updatePassword){

        String login = jwt.getSubject();

        try{
            UserResponseDTO userDTO = userService.updatePassword(login, updatePassword);
            return Response.ok(userDTO).build();
        } catch(Exception e){
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

}