package org.microdiamond.server.auth.services;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.microdiamond.server.auth.exceptions.LoginException;
import org.microdiamond.server.auth.restclients.UsersService;
import org.microdiamond.server.auth.restclients.beanparams.UsersBasicAuthCredentials;
import org.microdiamond.server.commons.beans.UserAuthInfo;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

    @Inject
    JWTService jwtService;

    public UserInfo login(HttpServerRequest request) throws LoginException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
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

    private UserInfo getUserInfo(UserAuthInfo userAuthInfo) throws LoginException, NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        if (userAuthInfo.getUsername().equals(appUsername))
        {
            validateAppPassword(userAuthInfo.getPassword());
            return getAppUserInfo();
        }
        return getUserInfoFromUsersMicroservice(userAuthInfo);
    }

    private UserInfo getUserInfoFromUsersMicroservice(UserAuthInfo userAuthInfo) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        String authTokenHeader = jwtService.generateAppTokenStringForHeader();
        String basicAuthCredentials = userAuthInfo.getBasicAuthCredentials();
        UsersBasicAuthCredentials usersBasicAuthCredentials = UsersBasicAuthCredentials.builder().
                authTokenHeader(authTokenHeader).
                credentials(basicAuthCredentials).
                build();
        return usersService.getByBasicAuthCredentials(usersBasicAuthCredentials);
    }

    private void validateAppPassword(String password) throws LoginException {
        if (!password.equals(appPassword))
        {
            throw new LoginException("Error login app user");
        }
    }
}