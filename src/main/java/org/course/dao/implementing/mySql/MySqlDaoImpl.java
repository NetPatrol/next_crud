package org.course.dao.implementing.mySql;

import org.course.dao.dao.Dao;
import org.course.dao.implementing.mySql.Dependency.Dependency;
import org.course.model.Model;
import org.course.model.permission.Role;
import org.course.model.phone.Phone;
import org.course.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public abstract class MySqlDaoImpl<T extends Model> extends Dependency implements Dao<T> {

    @Override
    public List<T> selectAll(Class<T> cls) {
        if (cls == User.class) {
            return entityManager
            .createQuery("from User u join fetch u.roles join fetch u.phones", cls)
            .getResultList();
        } else if (cls == Role.class) {
            return entityManager
            .createQuery("from Role r join fetch r.users", cls)
            .getResultList();
        } else if (cls == Phone.class) {
            return entityManager
            .createQuery("from Phone p join fetch p.users", cls)
            .getResultList();
        }
        return null;
    }

    @Override
    public T selectById(Class<T> cls, long id) {
        if (cls == User.class) {
            return entityManager.createQuery("from User u join fetch u.roles join fetch u.phones WHERE u.id = :id", cls)
            .setParameter("id", id)
            .getSingleResult();
        } else {
            return entityManager.find(cls, id);
        }

    }

    @Override
    public T selectByData(Class<T> cls, String s) {
        if (cls == User.class) {
            return entityManager.createQuery("from User u WHERE u.login = :login", cls)
            .setParameter("login", s)
            .getSingleResult();
        } else if (cls == Role.class) {
            return entityManager.createQuery("from Role r WHERE r.role = :role", cls)
            .setParameter("role", s)
            .getSingleResult();
        } else if (cls == Phone.class) {
            return entityManager.createQuery("from Phone p WHERE p.phone = :phone", cls)
            .setParameter("phone", s)
            .getSingleResult();
        }
        return null;
    }

    @Override
    public void save(T obj) {
        if (obj.getClass() == User.class) {
            User user = (User) obj;
            (user).setRoles(set(roleDao.selectById(Role.class, 2L)));
            (user).setPassword(userDao.passwordEncoder(user.getPassword()));
            entityManager.persist(user);
            entityManager.flush();
        } else {
            entityManager.persist(obj);
        }
    }

    @Override
    public <E extends Model> Set<E> set(E obj) {
        Set<E> set = new HashSet<>();
        set.add(obj);
        return set;
    }

    @Override
    public void bind(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void delete(Class<T> cls, long id) {
        entityManager.remove(selectById(cls, id));
    }
}
