package org.course.dao.implementing.mySql.descendants;

import org.course.dao.dao.descendants.PhoneDao;
import org.course.dao.implementing.mySql.MySqlDaoImpl;
import org.course.model.phone.Phone;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneMySqlDaoImpl extends MySqlDaoImpl<Phone> implements PhoneDao {
    @Override
    public void edit(long id, Phone phone) {
        Phone p = phoneDao.selectById(Phone.class, id);
        p.setPhone(phone.getPhone());
        entityManager.merge(p);
        entityManager.flush();
    }
}
