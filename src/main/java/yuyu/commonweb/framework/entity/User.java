package yuyu.commonweb.framework.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {

    Integer id;
    String username;
    String password;
    String salt;
    Date createDate;
    String createPerson;
    Date updateDate;
    String updatePerson;

    List<Role> roles;


}
