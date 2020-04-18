package org.microdiamond.server.auth.restclients;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.microdiamond.server.auth.restclients.beanparams.UsersBasicAuthCredentials;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@RegisterRestClient(configKey="md-users")
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UsersService {

    @GET
    @Path("health-check")
    Response healthCheck();

    @GET
    @Path("/basic-auth-credentials/{credentials}")
    UserInfo getByBasicAuthCredentials(@BeanParam UsersBasicAuthCredentials usersBasicAuthCredentials);
}
