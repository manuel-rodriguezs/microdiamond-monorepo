package org.microdiamond.server.users.entities;

import lombok.Data;
import org.microdiamond.server.commons.beans.Roles;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "roles")
    public List<User> users;

    @Column(unique = true)
    public Roles role;
}
