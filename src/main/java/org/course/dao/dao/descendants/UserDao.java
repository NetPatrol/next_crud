package org.course.dao.dao.descendants;

import org.course.dao.dao.Dao;
import org.course.model.user.User;

public interface UserDao extends Dao<User> {
    User selectForAutorize(String s);
    String passwordEncoder(String pass);
    void edit(long id, User user);
}
