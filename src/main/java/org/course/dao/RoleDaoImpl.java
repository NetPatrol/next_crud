package org.course.dao;

import org.course.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    private void initRole() {
        Role admin = new Role(1L, "ADMIN");
        Role user = new Role(2L, "ADMIN");
        if (entityManager.find(Role.class, admin.getId()) == null & entityManager.find(Role.class, user.getId()) == null) {
            entityManager.persist(admin);
            entityManager.persist(user);
        }
    }

    @Override
    @Transactional
    public Role getRole(long id) {
        return entityManager.find(Role.class, id);
    }
}

