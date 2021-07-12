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
}
