package org.microdiamond.server.auth;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AuthResourceTest {

    @Test
    @Disabled
    public void testHelloEndpoint() {
        given()
          .when().get("/")
          .then()
             .statusCode(200)
             .body(is("Users says hello"));
    }

}