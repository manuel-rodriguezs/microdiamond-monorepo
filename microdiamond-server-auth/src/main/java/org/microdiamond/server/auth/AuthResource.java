package org.microdiamond.server.auth;

import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.microdiamond.server.auth.exceptions.LoginException;
import org.microdiamond.server.auth.services.JWTService;
import org.microdiamond.server.auth.services.LoginService;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("/")
@RequestScoped
@Slf4j
public class AuthResource {

    @Inject
    JWTService service;

    @Inject
    LoginService loginService;

    @GET
    @Path("health-check")
    public Response healthCheck() {
        return Response.ok().build();
    }

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context RoutingContext rc) {
        try {
            UserInfo userInfo = loginService.login(rc.request());
            String token = service.generateTokenString(userInfo);
            return Response.ok()
                    .entity(Json.createObjectBuilder().add("token", token).build())
                    .build();
        } catch (LoginException e) {
            throw new WebApplicationException(e.getMessage());
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new WebApplicationException("Unexpected error");
        }
    }
}