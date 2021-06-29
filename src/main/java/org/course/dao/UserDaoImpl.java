package org.course.dao;

import org.course.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User u) {
        if (!ObjectUtils.isEmpty(u) && !entityManager.contains(u)) {
            if (selectById(u.getId()) == null && u.getName() != null
                && u.getLastName() != null && u.getAge() != 0) {
                entityManager.persist(u);
                entityManager.flush();
            } else {
                System.out.println("Такой пользователь уже есть");
            }
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> selectAll() {
        return entityManager.createQuery("from User u").getResultList();
    }

    @Override
    @Transactional
    public User selectById(long id){ return entityManager.find(User.class, id); };

    @Override
    @Transactional
    public void update(long id, User upd) {
        User user = selectById(id);
        user.setName(upd.getName());
        user.setLastName(upd.getLastName());
        user.setAge(upd.getAge());
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!(id <= 0) && selectById(id) != null) entityManager.remove(selectById(id));
    }
}
