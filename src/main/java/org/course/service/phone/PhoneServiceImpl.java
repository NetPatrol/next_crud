package org.course.service.phone;

import org.course.dao.dao.descendants.PhoneDao;
import org.course.model.Model;
import org.course.model.phone.Phone;
import org.course.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

    private final PhoneDao dao;
    @Autowired
    public PhoneServiceImpl(@Qualifier("phoneMySqlDaoImpl") PhoneDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void save(Phone phone) {
          dao.save(phone);
    }

    @Override
    @Transactional
    public List<Phone> selectAll(Class<Phone> cls) {
        return dao.selectAll(cls);
    }

    @Override
    @Transactional
    public Phone selectById(Class<Phone> cls, long id) {
        return dao.selectById(cls, id);
    }

    @Override
    @Transactional
    public Model selectByData(Class<Phone> cls, String s) {
        return dao.selectByData(cls, s);
    }

    @Override
    @Transactional
    public void edit(long id, Phone phone) {
        if (phone != null) {
            dao.edit(id, phone);
        }
    }

    @Override
    @Transactional
    public void delete(Class<Phone> cls, long id) {
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
