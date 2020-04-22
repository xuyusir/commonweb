package yuyu.commonweb.framework.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yuyu.commonweb.framework.entity.AjaxResult;
import yuyu.commonweb.framework.entity.user.User;
import yuyu.commonweb.framework.exception.CustomUnauthorizedException;
import yuyu.commonweb.framework.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("getmenus")
    public AjaxResult getMenusByUserId(Integer id) {
        if (id == null) {
            return new AjaxResult(AjaxResult.Type.ERROR, "error");
        }
        return new AjaxResult(AjaxResult.Type.SUCCESS, "success", userService.findMenusByUserId(id));
    }

    @GetMapping("getusers")
    public AjaxResult getAllUsers() {
        return new AjaxResult(AjaxResult.Type.SUCCESS, "success", userService.findAllusers());
    }

    @GetMapping("getroles")
    public AjaxResult getAllRoles() {
        return new AjaxResult(AjaxResult.Type.SUCCESS, "success", userService.findAllRoles());
    }

    @PostMapping("adduser")
    public AjaxResult addUser(@RequestBody User user){

        if(userService.findUserAllByUsername(user.getUsername()) != null){
            throw new CustomUnauthorizedException("该帐号已存在(Account exist.)");
        }

        if(userService.addUser(user)!=0){
            return new AjaxResult(AjaxResult.Type.SUCCESS,"success");
        }
        return new AjaxResult(AjaxResult.Type.ERROR,"error");
    }

}
