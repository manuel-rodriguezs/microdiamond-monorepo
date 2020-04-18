package org.microdiamond.server.commons.beans;

import lombok.Value;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Value
public class UserAuthInfo {
    String username;
    String password;
    String basicAuthCredentials;

    public static UserAuthInfo of(String basicAuthCredentials) {
        basicAuthCredentials = cleanBasicAuthCredentials(basicAuthCredentials);
        String decodedBasicAuthCredentials = decodeBasicAuthCredentials(basicAuthCredentials);
        String[] usernameAndPassword = decodedBasicAuthCredentials.split(":");
        String username = usernameAndPassword[0];
        String password = usernameAndPassword[1];

        return new UserAuthInfo(username, password, basicAuthCredentials);
    }

    private static String cleanBasicAuthCredentials(String basicAuthCredentials) {
        basicAuthCredentials = basicAuthCredentials.replaceAll("Basic ", "");
        basicAuthCredentials.trim();
        return basicAuthCredentials;
    }

    private static String decodeBasicAuthCredentials(String basicAuthCredentials) {
        byte[] decodedAuthCredentials = Base64.getDecoder().decode(basicAuthCredentials);
        return new String(decodedAuthCredentials, 0, decodedAuthCredentials.length, StandardCharsets.UTF_8);
    }
}
