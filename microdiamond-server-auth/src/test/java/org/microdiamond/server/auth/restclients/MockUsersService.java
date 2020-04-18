package org.microdiamond.server.auth.restclients;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.microdiamond.server.auth.restclients.UsersService;
import org.microdiamond.server.commons.beans.UserInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;


@Mock
@ApplicationScoped
@RestClient
public class MockUsersService implements UsersService {


    @Override
    public Response healthCheck() {
        return null;
    }

    @Override
    public UserInfo getByBasicAuthCredentials(String credentials) {
        return null;
    }
}
