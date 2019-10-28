package tedu.bao.day17.service;

import cn.bao.annotation.Autowride;
import cn.bao.annotation.Service;
import cn.bao.dao.UserDao;

@Service
public class UserService {
    @Autowride
    private UserDao userDao;

    public void test(){
        System.out.println("-----------------------UserService---------------------");
        userDao.prinnt();
    }
}
