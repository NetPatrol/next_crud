package org.course.dao.implementing.mySql.descendants;

import org.course.dao.dao.descendants.UserDao;
import org.course.dao.implementing.mySql.MySqlDaoImpl;
import org.course.model.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserMySqlDaoImpl extends MySqlDaoImpl<User> implements UserDao {

    @Override
    public User selectForAutorize(String s) {
        return entityManager.createQuery("from User u join fetch u.roles " +
                "WHERE u.login = :login", User.class)
                .setParameter("login", s)
                .getSingleResult();
    }

    public String passwordEncoder(String pass) {
        return passwordEncoder.encode(pass);
    }

    @Override
    public void edit(long id, User user) {
        User u = userDao.selectById(User.class, id);
        if (user.getName() != null) {
            if (!user.getName().equals("")) {
            u.setName(user.getName());
            }
        }
        if (user.getLastName() != null) {
            if (!user.getLastName().equals("")) {
            u.setLastName(user.getLastName());
            }
        }
        if (user.getLogin() != null) {
            if (!user.getLogin().equals("")) {
                u.setLogin(user.getLogin());
            }
        }
        if (user.getPassword() != null && user.getConfirmPassword() != null) {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                u.setPassword(passwordEncoder(user.getPassword()));
            }
        }
        entityManager.merge(u);
        entityManager.flush();
    }
}
