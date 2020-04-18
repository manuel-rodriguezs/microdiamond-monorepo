package org.microdiamond.server.users.services;

import org.microdiamond.server.commons.beans.Roles;
import org.microdiamond.server.users.entities.Role;
import org.microdiamond.server.users.repositories.RoleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class RoleService extends RoleRepository {

    @Transactional
    public Role findAndCreateIfNotExists(Roles roles) {
        Optional<Role> opRole = findByRole(roles);
        if (opRole.isEmpty()) {
            Role role = new Role();
            role.setRole(roles);
            persist(role);
            return role;
        } else {
            return opRole.get();
        }
    }


}
