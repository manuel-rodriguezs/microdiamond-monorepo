package org.microdiamond.server.auth.restclients.beanparams;

import lombok.Builder;
import lombok.Data;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.HeaderParam;

@Data
@Builder
public class UsersBasicAuthCredentials {
    @HeaderParam("Authorization")
    String authTokenHeader;

    @PathParam("credentials")
    String credentials;
}
