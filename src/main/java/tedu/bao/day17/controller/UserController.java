package tedu.bao.day17.controller;


import tedu.bao.day17.annotation.Autowride;
import tedu.bao.day17.annotation.Controller;
import tedu.bao.day17.service.UserService;

@Controller
public class UserController {

    @Autowride
    private UserService userService;

    public void test(){
        System.out.println("--------------------UserController----------------------");
        userService.test();
    }

}
