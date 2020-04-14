package org.microdiamond.server.auth.restclients;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.microdiamond.server.auth.restclients.UsersService;

import javax.enterprise.context.ApplicationScoped;


@Mock
@ApplicationScoped
@RestClient
public class MockUsersService implements UsersService {

    @Override
    public String hello() {
        return "hello";
    }
}
