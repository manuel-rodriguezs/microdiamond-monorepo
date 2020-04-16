package org.microdiamond.server.auth.beans;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
@Builder
public class UserInfo {
    String username;
    Date birthdate;
    List<String> roles;
}
