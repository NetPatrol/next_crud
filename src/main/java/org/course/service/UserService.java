package org.course.service;

import org.course.model.User;

import java.util.List;

public interface UserService {
    void save(User u);
    List<User> select();
    User select(long id);
    User select(String login);
    void edit(long id, User u);
    void edit(long id, String role);
    void delete(long id);
}
