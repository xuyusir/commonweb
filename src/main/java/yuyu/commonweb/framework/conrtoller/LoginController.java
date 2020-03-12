package yuyu.commonweb.framework.conrtoller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yuyu.commonweb.framework.entity.AjaxResult;
import yuyu.commonweb.framework.entity.user.User;
import yuyu.commonweb.framework.service.UserService;
import yuyu.commonweb.framework.util.RedisUtil;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @PostMapping("login")
    public AjaxResult login(@RequestBody HashMap<String, String> map) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(map.get("username"), map.get("password"),
                map.get("rememberMe"));


        try {
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(AjaxResult.Type.ERROR, "error");
        }

        User user = userService.findUserAllByUsername(map.get("username"));

        return new AjaxResult(AjaxResult.Type.SUCCESS, "success", user);

    }

    @PostMapping("logout")
    public AjaxResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new AjaxResult(AjaxResult.Type.SUCCESS,"success");

    }


    @GetMapping("test")
    public void testredis(){
        redisUtil.set("xuyu","fuck");
    }

}
