package org.microdiamond.server.auth.services;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.joda.time.DateTime;
import org.microdiamond.server.auth.beans.UserInfo;
import org.microdiamond.server.auth.restclients.UsersService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
public class LoginRequestService {

    private final String AUTHORIZATION_HEADER = "Authorization";

    @Inject
    @RestClient
    UsersService usersService;

    public UserInfo login(HttpServerRequest request) {
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

    private UserInfo getUserInfo(String username, String password) {
        List<String> roles = new ArrayList<>();
        roles.add("tester");
        roles.add("subscriber");

        return UserInfo.builder().
                username(username).
                birthdate(new DateTime(1982, 5, 24, 0, 0).toDate()).
                roles(roles).
                build();
    }
}
