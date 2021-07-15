package org.course.service.user;

import org.course.dao.dao.descendants.UserDao;
import org.course.model.Model;
import org.course.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao dao;
    @Autowired
    public UserServiceImpl(@Qualifier("userMySqlDaoImpl") UserDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public <T extends Model> void bind(User user) {
        dao.bind(user);
    }

    @Override
    @Transactional
    public <E extends Model> Set<E> set(E obj) {
        return dao.set(obj);
    }

    @Override
    @Transactional
    public void save(User user) {
        dao.save(user);
    }

    @Override
    @Transactional
    public List<User> selectAll(Class<User> cls) {
        return dao.selectAll(cls);
    }

    @Override
    @Transactional
    public User selectById(Class<User> cls, long id) {
        return dao.selectById(cls, id);
    }

    @Override
    @Transactional
    public Model selectByData(Class<User> cls, String s) {
        return dao.selectByData(cls, s);
    }

    @Transactional
    @Override
    public void edit(long id, User user) {
        if (user != null) {
            dao.edit(id, user);
        }

    }

    @Override
    @Transactional
    public void delete(Class<User> cls, long id) {
        dao.delete(cls, id);
    }

    @Override
    @Transactional
    public User selectForAutorize(String s) {
        return dao.selectForAutorize(s);
    }

    @Override
    @Transactional
    public String passwordEncoder(String pass) {
       return dao.passwordEncoder(pass);
    }
}
