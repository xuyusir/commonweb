package yuyu.commonweb.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuyu.commonweb.framework.dao.UserDao;
import yuyu.commonweb.framework.entity.User;
import yuyu.commonweb.framework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User findUserAllByUsername(String username) {
        return userDao.selectUserAllByUsername(username);
    }

    public int insertUser(User user){

    }

}
