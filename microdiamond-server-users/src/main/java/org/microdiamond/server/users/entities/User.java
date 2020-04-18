package org.microdiamond.server.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.Data;
import org.microdiamond.server.commons.beans.UserInfo;
import org.microdiamond.server.users.utils.BcryptPasswordUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String surname;
    private Date birthdate;
    private UserStatus status = UserStatus.ENABLED;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    public void setPassword(String password) {
        this.password = BcryptPasswordUtil.bcryptHashPassword(password);
    }

    @Override
    public String toString() {
        return "User(id=" + id + ", username=" + username + ", name=" + name +
                ", surname=" + surname + ", birthdate=" + birthdate +
                ", status=" + status + ", roles=" + getRolesListStrings().toString();
    }

    public UserInfo toUserInfo() {
        List<String> roles = getRolesListStrings();

        return UserInfo.builder().
                username(getUsername()).
                name(getName()).
                surname(getSurname()).
                birthdate(getBirthdate()).
                roles(roles).
                build();
    }

    private List<String> getRolesListStrings() {
        return getRoles().stream().
                    map(role -> role.getRole().toString()).
                    collect(Collectors.toList());
    }
}
