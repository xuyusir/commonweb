package yuyu.commonweb.framework.entity.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户ID
    private Integer userId;
    //token
    private String token;
    //过期时间
    private Date expireTime;
    //更新时间
    private Date updateTime;

}
