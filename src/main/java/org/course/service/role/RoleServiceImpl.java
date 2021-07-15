package org.course.service.role;

import org.course.dao.dao.descendants.RoleDao;
import org.course.model.Model;
import org.course.model.permission.Role;
import org.course.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleDao dao;
    @Autowired
    public void setDao(@Qualifier("roleMySqlDaoImpl") RoleDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void save(Role obj) {
         dao.save(obj);
    }

    @Override
    @Transactional
    public List<Role> selectAll(Class<Role> cls) {
        return dao.selectAll(cls);
    }

    @Override
    @Transactional
    public Role selectById(Class<Role> cls, long id) {
        return dao.selectById(cls, id);
    }

    @Override
    @Transactional
    public Model selectByData(Class<Role> cls, String s) {
        return dao.selectByData(cls, s);
    }

    @Override
    @Transactional
    public void delete(Class<Role> cls, long id) {
        dao.delete(cls, id);
    }

    @Override
    @Transactional
    public <E extends Model> void bind(User user) {
        dao.bind(user);
    }

    @Override
    @Transactional
    public <E extends Model> Set<E> set(E obj) {
        return dao.set(obj);
    }
}
