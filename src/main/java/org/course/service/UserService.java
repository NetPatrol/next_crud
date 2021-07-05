package org.course.service;

import org.course.model.User;

import java.util.List;

public interface UserService {
    void save(User u);
    List<User> selectAll();
    User selectById(long id);
    User selectByLogin(String login);
    void update(long id, User u);
    void editRole(long id, String role);
    void delete(long id);
}
