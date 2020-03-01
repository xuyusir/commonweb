package yuyu.commonweb.framework.dao;


import org.springframework.stereotype.Repository;
import yuyu.commonweb.framework.entity.Permission;
import yuyu.commonweb.framework.entity.Role;
import yuyu.commonweb.framework.entity.User;

@Repository
public interface UserDao {

    User selectUserByUsername(String username);

    User selectUserByUserId(Integer id);

    Role selectRoleByRoleId(Integer id);

    Permission selectPerminssByPerminssId(Integer id);

    User selectUserAllByUsername(String username);


}
