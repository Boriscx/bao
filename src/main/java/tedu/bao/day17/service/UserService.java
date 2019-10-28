package tedu.bao.day17.service;

import tedu.bao.day17.annotation.Autowride;
import tedu.bao.day17.annotation.Service;
import tedu.bao.day17.dao.UserDao;

@Service
public class UserService {
    @Autowride
    private UserDao userDao;

    public void test(){
        System.out.println("-----------------------UserService---------------------");
        userDao.prinnt();
    }
}
