package org.microdiamond.server.users.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.entities.UserStatus;
import org.microdiamond.server.users.utils.BcryptPasswordUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class UserRepository implements PanacheRepository<User> {

    @Inject
    EntityManager em;

    public Optional<User> findByUsername(String username){
        return findByUsernameAndUserStatus(username, UserStatus.ENABLED);
    }

    public Optional<User> findByUsernameAndUserStatus(String username, UserStatus status) {
        try {
            User user = em.
                    createQuery("FROM User WHERE username = :username AND status = :status", User.class).
                    setParameter("username", username).
                    setParameter("status", status).
                    getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        try {
            User user = em.
                    createQuery("FROM User WHERE username = :username AND password = :password AND status = :status", User.class).
                    setParameter("username", username).
                    setParameter("password", BcryptPasswordUtil.bcryptHashPassword(password)).
                    setParameter("status", UserStatus.ENABLED).
                    getSingleResult();
            return Optional.of(user);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
