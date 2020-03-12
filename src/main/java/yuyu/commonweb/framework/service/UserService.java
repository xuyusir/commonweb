package yuyu.commonweb.framework.service;

import yuyu.commonweb.framework.entity.user.Permission;
import yuyu.commonweb.framework.entity.user.Role;
import yuyu.commonweb.framework.entity.user.User;

import java.util.Set;


public interface UserService {

    User findUserAllByUsername(String username);

    int addUser(User user);

    Set<Permission> findMenusByUserId(Integer id);

    Set<User> findAllusers();

    Set<Role> findAllRoles();



}
