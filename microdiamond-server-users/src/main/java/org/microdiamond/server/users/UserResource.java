package org.microdiamond.server.users;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.services.UserService;

import javax.inject.Inject;
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
}