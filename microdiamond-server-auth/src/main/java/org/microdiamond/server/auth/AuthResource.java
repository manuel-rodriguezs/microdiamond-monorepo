package org.microdiamond.server.auth;

import io.vertx.ext.web.RoutingContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.microdiamond.server.auth.beans.UserInfo;
import org.microdiamond.server.auth.services.JWTService;
import org.microdiamond.server.auth.services.LoginService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/")
@RequestScoped
public class AuthResource {
    @Inject
    JsonWebToken jwt;

    @Inject
    JWTService service;

    @Inject
    LoginService loginService;

    @GET
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@Context RoutingContext rc) throws Exception {
        UserInfo userInfo = loginService.login(rc.request());
        return service.generateTokenString(userInfo);
    }

    @GET()
    @Path("roles-allowed")
    @RolesAllowed({"subscriber"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt.getClaimNames() != null;
        return String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
    }
}