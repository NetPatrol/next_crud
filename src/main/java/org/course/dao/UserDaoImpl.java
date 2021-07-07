package org.course.dao;

import lombok.SneakyThrows;
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
    @SneakyThrows
    @Transactional
    public void save(User user) {
        if (!ObjectUtils.isEmpty(user.getLogin())
                && !entityManager.contains(user.getLogin())
                && !entityManager.contains(user.getPassword())
                && !entityManager.contains(user.getConfirmPassword())) {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                Set<Role> roles = new HashSet<>();
                roles.add(roleDao.getRole(2));
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(roles);
                entityManager.persist(user);
                entityManager.flush();
            } else {
                throw new Exception("Проверьте правильность ввода пароля");
            }
        } else {
            throw new Exception("Это имя уже занято");
        }
    }

    @Override
    @Transactional
    public List<User> select() {
        return entityManager.createQuery("from User u join fetch u.roles", User.class).getResultList();
    }

    @Override
    @Transactional
    public User select(long id){ return entityManager.find(User.class, id); }

    @Override
    @Transactional
    public User select(String login) {
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
    public void edit(long id, User upd) {
        User user = select(id);
        if (!user.getPassword().equals(upd.getPassword())) {
            user.setPassword(passwordEncoder.encode(upd.getPassword()));
            entityManager.merge(user);
            entityManager.flush();
        }
    }
    @Override
    @Transactional
    public void edit(long id, String role) {
        User user = select(id);
        Set<Role> roles = new HashSet<>();
        if (role.equals("admin")) {
            roles.add(roleDao.getRole(1)); //admin
        } else if (role.equals("user")) {
            roles.add(roleDao.getRole(2)); //user
        } else {
            roles.add(roleDao.getRole(3)); //guest
        }
        user.setRoles(roles);
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void delete(long id) {
        entityManager.remove(select(id));
    }
}
