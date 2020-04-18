package org.microdiamond.server.users.services;

import lombok.extern.slf4j.Slf4j;
import org.microdiamond.server.commons.beans.Roles;
import org.microdiamond.server.commons.beans.UserAuthInfo;
import org.microdiamond.server.users.entities.Role;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class UserService extends UserRepository {
    @Inject
    RoleService roleService;

    @Transactional
    public void register(User user) {
        Role roleUser = roleService.findAndCreateIfNotExists(Roles.USER);
        user.setRoles(Collections.singletonList(roleUser));
        persist(user);
    }

    public User findByBasicAuthCredentials(String credentials) throws EntityNotFoundException {
        UserAuthInfo userAuthInfo = UserAuthInfo.of(credentials);
        Optional<User> opUser = findByUsernameAndPassword(userAuthInfo.getUsername(), userAuthInfo.getPassword());
        if (opUser.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return opUser.get();
    }
}
