package org.course.dao;

import org.course.model.User;

import java.util.List;

public interface UserDao{
    void save(User u);
    List<User> selectAll();
    User selectById(long id);
    User selectByLogin(String login);
    void update(long id, User upd);
    void editRole(long id, String role);
    void delete(long id);
}
