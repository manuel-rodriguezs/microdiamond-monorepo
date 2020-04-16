package org.microdiamond.server.auth.services;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.joda.time.DateTime;
import org.microdiamond.server.auth.beans.UserInfo;
import org.microdiamond.server.auth.exceptions.LoginException;
import org.microdiamond.server.auth.restclients.UsersService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

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
        String encodedHeader = getEncodedAuthorizationHeader(request);
        String decodedHeaderString = decodeAuthorizationHeader(encodedHeader);
        String username = getUsernameFromDecodedAuthorizationHeader(decodedHeaderString);
        String password = getPasswordFromDecodedAuthorizationHeader(decodedHeaderString);
        return getUserInfo(username, password);
    }

    private String getEncodedAuthorizationHeader(HttpServerRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        header = header.replaceAll("Basic ", "");
        header.trim();
        return header;
    }

    private String decodeAuthorizationHeader(String encodedHeader) {
        byte[] decodedHeaderBytes = Base64.getDecoder().decode(encodedHeader);
        return new String(decodedHeaderBytes, 0, decodedHeaderBytes.length, StandardCharsets.UTF_8);
    }

    private String getUsernameFromDecodedAuthorizationHeader(String decodedHeaderString) {
        String[] usernameAndPassword = decodedHeaderString.split(":");
        return usernameAndPassword[0];
    }

    private String getPasswordFromDecodedAuthorizationHeader(String decodedHeaderString) {
        String[] usernameAndPassword = decodedHeaderString.split(":");
        return usernameAndPassword[1];
    }

    private UserInfo getUserInfo(String username, String password) throws LoginException {
        if (username.equals(appUsername))
        {
            validateAppPassword(password);
            return getAppUserInfo();
        }
        return UserInfo.builder().
                username(username).
                birthdate(new DateTime(1982, 5, 24, 0, 0).toDate()).
                roles(Arrays.asList("tester", "subscriber")).
                build();
    }

    private void validateAppPassword(String password) throws LoginException {
        if (!password.equals(appPassword))
        {
            throw new LoginException("Error login app user");
        }
    }

    private UserInfo getAppUserInfo() {
        return UserInfo.builder().
                username(appUsername).
                roles(UserInfo.getAppRoles()).
                build();
    }
}