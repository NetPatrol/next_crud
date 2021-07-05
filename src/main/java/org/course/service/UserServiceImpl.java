package org.course.service;

import org.course.dao.UserDao;
import org.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void save(User u) {
        dao.save(u);
    }

    @Override
    @Transactional
    public List<User> select() {
        return dao.select();
    }

    @Override
    @Transactional
    public User select(long id) {
        return dao.select(id);
    }

    @Override
    @Transactional
    public User select(String login) {
        return dao.select(login);
    }

    @Override
    @Transactional
    public void edit(long id, User u) {
       dao.edit(id, u);
    }

    @Override
    @Transactional
    public void edit(long id, String role) { dao.edit(id, role); }

    @Override
    @Transactional
    public void delete(long id) {
        dao.delete(id);
    }
}
