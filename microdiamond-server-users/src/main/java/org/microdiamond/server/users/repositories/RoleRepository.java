package org.microdiamond.server.users.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.microdiamond.server.commons.beans.Roles;
import org.microdiamond.server.users.entities.Role;
import org.microdiamond.server.users.entities.User;
import org.microdiamond.server.users.entities.UserStatus;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class RoleRepository implements PanacheRepository<Role> {

    @Inject
    EntityManager em;

    public Optional<Role> findByRole(Roles roles) {
        try {
            Role role = em.
                    createQuery("FROM Role WHERE role = :role", Role.class).
                    setParameter("role", roles).
                    getSingleResult();
            return Optional.of(role);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
