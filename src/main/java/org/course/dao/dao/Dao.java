package org.course.dao.dao;

import org.course.model.Model;
import org.course.model.user.User;

import java.util.List;
import java.util.Set;

public interface Dao<T extends Model> {
    void save(T obj);

    List<T> selectAll(Class<T> cls);

    T selectById(Class<T> cls, long id);

    Model selectByData(Class<T> cls, String s);

    void delete(Class<T> cls, long id);

    <E extends Model> void bind(User user);

    <E extends Model> Set<E> set(E obj);

}
