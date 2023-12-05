package br.unitins.topicos1.resource;

import java.io.IOException;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.topicos1.service.AddressService;
import br.unitins.topicos1.service.UserFileService;
import br.unitins.topicos1.service.UserService;
import br.unitins.topicos1.application.Error;
import br.unitins.topicos1.dto.CompleteUserDTO;
import br.unitins.topicos1.dto.CompleteUserResponseDTO;
import br.unitins.topicos1.dto.EmailDTO;
import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UpdatePasswordDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.dto.UsernameDTO;
import br.unitins.topicos1.form.UserImageForm;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.PhoneRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
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
import br.unitins.topicos1.validation.ValidationException;

import br.unitins.topicos1.application.Error;

@Path("/loggedUser")
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

    @Inject
    AddressService addressService;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @GET
    @RolesAllowed({ "User", "Admin" })
    public Response getUser() {

        // obtendo o login pelo token jwt
        String login = jwt.getSubject();

        return Response.ok(userService.findByEmail(login)).build();

    }




    // ---------- Image ----------

    @PATCH
    @Path("/upload/image/")
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

    // ---------- dados completos ----------

    @GET
    @Path("/find/completeUser/")
    @RolesAllowed({"User", "Admin"})
    public Response getCompleteUserByEmail(){
        String login = jwt.getSubject();
        return Response.ok(userService.findCompleteUserByEmail(login)).build();
    }

    @PATCH
    @Path("/complete/username/")
    @RolesAllowed({"User", "Admin"})
    public Response insertUsername(UsernameDTO usernameDTO){

        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        Long id = user.id();

        try{
            LOG.info("Completando usuario");
            UsernameDTO username = userService.insertUsername(id, usernameDTO);
            return Response.ok(usernameDTO).build();
        } catch(ValidationException e){
            LOG.error("Erro de validação");;
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        } catch(IllegalArgumentException e){
            LOG.error("Valor invalido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }

    }

    @PATCH
    @Path("/complete/register/")
    @RolesAllowed({"User", "Admin"})
    public Response completeUser(CompleteUserDTO dto){

        String login = jwt.getSubject();

        UserResponseDTO user = userService.findByEmail(login);

        Long id = user.id();

        try{
            LOG.info("Completando informações de usuario");
            CompleteUserResponseDTO completeUserDTO = userService.completeUser(id, dto);
            return Response.noContent().build();
        } catch(Exception e){
            LOG.error("Erro ao conpletar as informações");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    // ---------- Updates ----------

    @PATCH
    @Path("/update/email/")
    @RolesAllowed({"User", "Admin"})
    public Response updateEmail(EmailDTO newEmail){

        String login = jwt.getSubject();

        try{
            LOG.info("Alterando o email");
            UserResponseDTO userDTO = userService.updateEmail(login, newEmail);
            return Response.noContent().build();
        } catch(ValidationException e){
            LOG.error("Valor invalido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PATCH
    @Path("/update/username/")
    @RolesAllowed({"User", "Admin"})
    public Response updateUsername(UsernameDTO newUsername){

        String login = jwt.getSubject();

        try{
            LOG.info("Alterando o username");
            UserResponseDTO userDTO = userService.updateUsername(login, newUsername);
            return Response.noContent().build();
        } catch(ValidationException e){
            LOG.error("Valor invalido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PATCH
    @Path("/update/password/")
    @RolesAllowed({"User", "Admin"})
    public Response updatePassword(UpdatePasswordDTO updatePassword){

        String login = jwt.getSubject();

        try{
            LOG.info("Alterando a senha");
            UserResponseDTO userDTO = userService.updatePassword(login, updatePassword);
            return Response.noContent().build();
        } catch(ValidationException e){
            LOG.info("Valor invalido");
            e.printStackTrace();
            Error error = new Error("400", e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(error).build();
        } catch(ForbiddenException e){
            LOG.info("Acesso negado");
            e.printStackTrace();
            Error error = new Error("403", e.getMessage());
            return Response.status(Status.FORBIDDEN).entity(error).build();
        }
    }

    // ---------- Address ----------

    

}