package yuyu.commonweb.framework.conrtoller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yuyu.commonweb.framework.entity.AjaxResult;
import yuyu.commonweb.framework.service.UserService;

import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public AjaxResult login(@RequestBody HashMap<String, String> map) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(map.get("username"), map.get("password"),
                map.get("rememberMe"));


        try {
            subject.login(token);
        } catch (Exception e) {
            return new AjaxResult(AjaxResult.Type.ERROR, "error");
        }

        return new AjaxResult(AjaxResult.Type.SUCCESS, "success",
                userService.findUserAllByUsername(map.get("username")));

    }

}
