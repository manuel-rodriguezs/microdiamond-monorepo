package org.microdiamond.server.users;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.microdiamond.server.commons.beans.UserInfo;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class UserResource {

    @Inject
    UserService service;

    @GET
    @Path("health-check")
    public Response healthCheck() {
        return Response.ok().build();
    }

    @POST
    public Response register(User user) {
        try {
            log.info(String.format("Registering the user %s", user));
            service.register(user);
            return Response.ok().build();
        } catch (Exception e) {
            log.error(String.format("Error registering the user %s because %s", user, e));
            throw new WebApplicationException("Error registering the user", 500);
        }
    }

    @GET
    @Path("/basic-auth-credentials/{credentials}")
    @RolesAllowed({"APP"})
    public UserInfo getByBasicAuthCredentials(@PathParam String credentials) {
        try {
            User user = service.findByBasicAuthCredentials(credentials);
            return user.toUserInfo();
        } catch (EntityNotFoundException e) {
            log.info(String.format("User not found with credentials %s", credentials));
            throw new WebApplicationException("User not found", 404);
        }
    }
}