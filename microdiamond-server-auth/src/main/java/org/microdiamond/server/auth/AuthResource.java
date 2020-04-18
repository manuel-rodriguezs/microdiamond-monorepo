package org.microdiamond.server.auth;

import io.vertx.ext.web.RoutingContext;
import org.microdiamond.server.auth.services.JWTService;
import org.microdiamond.server.auth.services.LoginService;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@RequestScoped
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
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@Context RoutingContext rc) throws Exception {
        UserInfo userInfo = loginService.login(rc.request());
        return service.generateTokenString(userInfo);
    }
}