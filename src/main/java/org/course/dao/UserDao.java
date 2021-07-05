package org.course.dao;

import org.course.model.User;

import java.util.List;

public interface UserDao{
    void save(User u);
    List<User> select();
    User select(long id);
    User select(String login);
    void edit(long id, User upd);
    void edit(long id, String role);
    void delete(long id);
}
