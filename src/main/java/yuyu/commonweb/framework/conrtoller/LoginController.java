package yuyu.commonweb.framework.conrtoller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yuyu.commonweb.framework.entity.AjaxResult;
import yuyu.commonweb.framework.entity.Constant;
import yuyu.commonweb.framework.entity.user.User;
import yuyu.commonweb.framework.exception.CustomUnauthorizedException;
import yuyu.commonweb.framework.service.UserService;
import yuyu.commonweb.framework.util.AesCipherUtil;
import yuyu.commonweb.framework.util.JedisUtil;
import yuyu.commonweb.framework.util.JwtUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class LoginController {

    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    UserService userService;

    @PostMapping("login")
    public AjaxResult login(@RequestBody HashMap<String, String> map, HttpServletResponse httpServletResponse) {

        //判断是否有此账号
        User user = userService.findUserAllByUsername(map.get("username"));
        if (user == null) {
            throw new CustomUnauthorizedException("该帐号不存在(The account does not exist.)");
        }

        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(user.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(map.get("username") + map.get("password"))) {
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + user.getUsername())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + user.getUsername());
            }
        }

        // 设置RefreshToken，时间戳为当   前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(user.getUsername(), currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

        return new AjaxResult(AjaxResult.Type.SUCCESS, "success", user);

    }

    @PostMapping("logout")
    public AjaxResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new AjaxResult(AjaxResult.Type.SUCCESS, "success");
    }


    @GetMapping("test")
    public void testredis() {

    }

}
