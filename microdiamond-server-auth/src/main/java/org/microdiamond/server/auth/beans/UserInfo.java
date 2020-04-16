package org.microdiamond.server.auth.beans;

import lombok.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class UserInfo {
    String username;
    Date birthdate;
    List<String> roles;
    long expirationSeconds;

    public boolean isAppUser() {
        return getRoles().equals(UserInfo.getAppRoles());
    }

    public static List<String> getAppRoles() {
        return Collections.singletonList("app");
    }
}
