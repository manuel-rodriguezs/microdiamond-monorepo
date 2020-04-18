package org.microdiamond.server.commons.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    String username;
    String name;
    String surname;
    Date birthdate;
    List<String> roles;

    @JsonIgnore
    public boolean isAppUser() {
        return getRoles().equals(UserInfo.getAppRoles());
    }

    public static List<String> getAppRoles() {
        return Collections.singletonList(Roles.APP.toString());
    }
}
