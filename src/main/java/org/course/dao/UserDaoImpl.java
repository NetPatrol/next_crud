package org.course.dao;

import org.course.model.Role;
import org.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;


    @Override
    @Transactional
    public void save(User u) {
        if (!ObjectUtils.isEmpty(u) && !entityManager.contains(u)) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleDao.getRole(2));
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setRoles(roles);
            entityManager.persist(u);
            entityManager.flush();
        } else {
            System.out.println("Такой пользователь уже есть");
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> selectAll() {
        return entityManager.createQuery("from User u").getResultList();
    }

    @Override
    @Transactional
    public User selectById(long id){ return entityManager.find(User.class, id); }

    @Override
    @Transactional
    public User selectByLogin(String login) {
        User user = entityManager
                .createQuery("SELECT u from User u join fetch u.roles WHERE u.login = :login", User.class)
                .setParameter("login", login).getSingleResult();
        if (user.getLogin().isEmpty()) {
             return null;
        }
        return user;
    }

    @Override
    @Transactional
    public void update(long id, User upd) {
        User user = selectById(id);
        if (!user.getPassword().equals(upd.getPassword())) {
            user.setPassword(passwordEncoder.encode(upd.getPassword()));
            entityManager.merge(user);
            entityManager.flush();
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        entityManager.remove(selectById(id));
    }
}
