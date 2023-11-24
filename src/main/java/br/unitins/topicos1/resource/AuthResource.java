package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.LoginDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import br.unitins.topicos1.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService service;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @POST
    public Response login(@Valid LoginDTO dto) {

        String hashSenha = hashService.getHashPassword(dto.password());

        UserResponseDTO result = service.findByEmailAndPassword(dto.email(), hashSenha);

        String token = jwtService.generateJwt(result);

        return Response.ok().header("Authorization", token).build();
    }
  
}