package org.microdiamond.server.users.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.entities.UserStatus;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

public class UserRepository implements PanacheRepository<User> {

    @Inject
    EntityManager entityManager;

    public Optional<User> findByName(String username){
        return findByNameAndUserStatus(username, UserStatus.ENABLED);
    }

    public Optional<User> findByNameAndUserStatus(String username, UserStatus status) {
        User user = entityManager.
                createQuery("FROM User WHERE username = :username AND status = :status", User.class).
                setParameter("username", username).
                setParameter("status", status).
                getSingleResult();
        if (user == null) {
            return Optional.empty();
        } else {
            return Optional.of(user);
        }
    }

}
