package yuyu.commonweb.framework.service;

import yuyu.commonweb.framework.entity.User;


public interface UserService {

    User findUserAllByUsername(String username);

    int insertUser(User user);

}
