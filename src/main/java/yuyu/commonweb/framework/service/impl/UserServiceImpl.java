package yuyu.commonweb.framework.service.impl;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yuyu.commonweb.framework.dao.UserDao;
import yuyu.commonweb.framework.entity.user.Permission;
import yuyu.commonweb.framework.entity.user.Role;
import yuyu.commonweb.framework.entity.user.User;
import yuyu.commonweb.framework.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User findUserAllByUsername(String username) {
        return userDao.selectUserAllByUsername(username);
    }

    @Transactional
    public int addUser(User user) {

        String salt = new SecureRandomNumberGenerator().nextBytes().toString(); //盐量随机
        user.setPassword(new SimpleHash("md5",user.getPassword(),salt,2).toString());
        user.setSalt(salt);

        int i=userDao.insertUser(user);
        User u=userDao.selectUserByUsername(user.getUsername());

        for (Role role : user.getRoles()) {
            userDao.insertUserRole(u,role);
        }

        return i;
    }

    @Override
    public Set<Permission> findMenusByUserId(Integer id) {

        //获取user所有信息
        User user = userDao.selectUserAllByUsername(userDao.selectUserByUserId(id).getUsername());
        Set<Permission> permissions1 = new HashSet<>();
        Set<Permission> permissions2 = new HashSet<>();

        //加入所有菜单
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                if (permission.getType().equals("menu")) {
                    permissions1.add(permission);
                }
            }
        }

        //递归根目录，注入所有子菜单
        for (Permission permission : permissions1) {
            if (permission.getParentId() == 0) {
                recursMenu(permissions1, permission);
            }
        }

        //过滤返回结果，只留下根菜单
        for (Permission permission : permissions1) {
            if (permission.getParentId() == 0) {
                permissions2.add(permission);
            }
        }
        return permissions2;
    }

    //递归菜单函数
    public void recursMenu(Set<Permission> permissions, Permission permission) {

        List<Permission> p1Child = new ArrayList<>();

        //若无子节点跳出
        for (Permission p1 : permissions) {
            if (p1.getParentId() == permission.getId()) {
                recursMenu(permissions, p1);
                p1Child.add(p1);
            }
        }

        if (p1Child != null) {
            permission.setChildren(p1Child);
        }

        return;
    }

    @Override
    public Set<User> findAllusers() {
        return userDao.selectAllUsers();
    }

    @Override
    public Set<Role> findAllRoles() {
        return userDao.findAllRoles();
    }


}
