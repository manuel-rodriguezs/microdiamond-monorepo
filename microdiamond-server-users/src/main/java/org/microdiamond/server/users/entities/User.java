package org.microdiamond.server.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String surname;
    private Date birthdate;
    private UserStatus status = UserStatus.ENABLED;

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }
}
