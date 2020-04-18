package org.microdiamond.server.auth.restclients;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @ClientHeaderParam(name="Authorization", value="{org.microdiamond.server.auth.services.JWTService.generateAppTokenStringForHeader}")
    UserInfo getByBasicAuthCredentials(@PathParam String credentials);
}
