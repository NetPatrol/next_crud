package org.course.dao.implementing.mySql.descendants;

import org.course.dao.dao.descendants.RoleDao;
import org.course.dao.implementing.mySql.MySqlDaoImpl;
import org.course.model.permission.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleMySqlDaoImpl extends MySqlDaoImpl<Role> implements RoleDao {

}

