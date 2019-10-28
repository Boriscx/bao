package tedu.bao.day17.dao;


import tedu.bao.day17.annotation.Autowride;
import tedu.bao.day17.annotation.Component;
import tedu.bao.day17.pojo.User;

@Component
public class UserDao {

    @Autowride
    private User user ;

    public void prinnt(){
        System.out.println("--------------------------UserDao--------------------------");
        System.out.println(user);
    }
}
