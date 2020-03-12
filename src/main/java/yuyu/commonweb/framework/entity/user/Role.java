package yuyu.commonweb.framework.entity.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Role {
    Integer id;
    String name;
    String description;
    String rols;
    Date createDate;
    String createPerson;
    Date updateDate;
    String updatePerson;

    List<Permission> permissions;
}
