package tedu.bao.day17.controller;

import cn.bao.annotation.Autowride;
import cn.bao.annotation.Controller;
import cn.bao.service.UserService;

@Controller
public class UserController {

    @Autowride
    private UserService userService;

    public void test(){
        System.out.println("--------------------UserController----------------------");
        userService.test();
    }

}
