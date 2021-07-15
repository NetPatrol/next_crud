package org.course.dao.implementing.mySql.Dependency;

import org.course.dao.dao.descendants.PhoneDao;
import org.course.dao.dao.descendants.RoleDao;
import org.course.dao.dao.descendants.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class Dependency {
    @PersistenceContext
    protected EntityManager entityManager;

    protected BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected RoleDao roleDao;
    @Autowired
    public void setRoleDao(@Qualifier("roleMySqlDaoImpl")RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    protected PhoneDao phoneDao;
    @Autowired
    public void setRoleDao(@Qualifier("phoneMySqlDaoImpl") PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    protected UserDao userDao;
    @Autowired
    public void setRoleDao(@Qualifier("userMySqlDaoImpl") UserDao userDao) {
        this.userDao = userDao;
    }
}
