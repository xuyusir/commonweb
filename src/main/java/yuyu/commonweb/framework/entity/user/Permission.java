package yuyu.commonweb.framework.entity.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Permission {

    Integer id;
    String name;
    String pers;
    String description;
    Integer parentId;
    String parentIds;
    String url;
    String type;
    Date createDate;
    String createPerson;
    Date updateDate;
    String updatePerson;

    List<Permission> children;
}
