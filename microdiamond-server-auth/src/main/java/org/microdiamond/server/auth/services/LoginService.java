package org.microdiamond.server.auth.services;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.microdiamond.server.auth.exceptions.LoginException;
import org.microdiamond.server.auth.restclients.UsersService;
import org.microdiamond.server.commons.beans.UserAuthInfo;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Date;

@ApplicationScoped
public class LoginService {

    final String AUTHORIZATION_HEADER = "Authorization";

    @ConfigProperty(name = "microdiamond.app.username")
    String appUsername;

    @ConfigProperty(name = "microdiamond.app.password")
    String appPassword;

    @Inject
    @RestClient
    UsersService usersService;

    public UserInfo login(HttpServerRequest request) throws LoginException {
        String basicAuthCredentials = request.getHeader(AUTHORIZATION_HEADER);
        UserAuthInfo userAuthInfo = UserAuthInfo.of(basicAuthCredentials);
        return getUserInfo(userAuthInfo);
    }

    public UserInfo getAppUserInfo() {
        return UserInfo.builder().
                username(appUsername).
                name(appUsername).
                surname("").
                birthdate(new Date(0)).
                roles(UserInfo.getAppRoles()).
                build();
    }

    private UserInfo getUserInfo(UserAuthInfo userAuthInfo) throws LoginException, WebApplicationException {
        if (userAuthInfo.getUsername().equals(appUsername))
        {
            validateAppPassword(userAuthInfo.getPassword());
            return getAppUserInfo();
        }
        return usersService.getByBasicAuthCredentials(userAuthInfo.getBasicAuthCredentials());
    }

    private void validateAppPassword(String password) throws LoginException {
        if (!password.equals(appPassword))
        {
            throw new LoginException("Error login app user");
        }
    }
}