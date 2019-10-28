package tedu.bao.day17.dao;

import cn.bao.annotation.Autowride;
import cn.bao.annotation.Component;
import cn.bao.pojo.User;

@Component
public class UserDao {

    @Autowride
    private User user ;

    public void prinnt(){
        System.out.println("--------------------------UserDao--------------------------");
        System.out.println(user);
    }
}
