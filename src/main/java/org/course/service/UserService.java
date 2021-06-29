package org.course.service;

import org.course.model.User;

import java.util.List;

public interface UserService {
    void save(User u);
    List<User> selectAll();
    User selectById(long id);
    void update(long id, User u);
    void delete(long id);
}
