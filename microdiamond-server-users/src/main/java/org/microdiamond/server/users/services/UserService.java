package org.microdiamond.server.users.services;

import lombok.extern.slf4j.Slf4j;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Slf4j
@ApplicationScoped
public class UserService extends UserRepository {
    @Transactional
    public void register(User user) {
        persist(user);
    }
}
