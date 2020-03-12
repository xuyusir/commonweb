package yuyu.commonweb.framework.dao;


import org.springframework.stereotype.Repository;
import yuyu.commonweb.framework.entity.user.Permission;
import yuyu.commonweb.framework.entity.user.Role;
import yuyu.commonweb.framework.entity.user.User;

import java.util.Set;

@Repository
public interface UserDao {

    User selectUserByUsername(String username);

    User selectUserByUserId(Integer id);

    Role selectRoleByRoleId(Integer id);

    Permission selectPerminssByPerminssId(Integer id);

    User selectUserAllByUsername(String username);

    Set<User> selectAllUsers();

    Set<Role> findAllRoles();

    int insertUser(User user);

    int insertUserRole(User user,Role role);



}
