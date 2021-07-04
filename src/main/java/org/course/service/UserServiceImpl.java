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
    public List<User> selectAll() {
        return dao.selectAll();
    }

    @Override
    @Transactional
    public User selectById(long id) {
        return dao.selectById(id);
    }

    @Override
    @Transactional
    public User selectByLogin(String login) {
        return dao.selectByLogin(login);
    }

    @Override
    @Transactional
    public void update(long id, User u) {
       dao.update(id, u);
    }

    @Override
    @Transactional
    public void delete(long id) {
        dao.delete(id);
    }
}
