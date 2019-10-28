package tedu.bao.day17.pojo;

import cn.bao.annotation.Entity;
import cn.bao.annotation.Value;

@Entity
public class User {

    @Value("${user.zhangsan.name}")
    private String name ;

    @Value("${user.zhangsan.age}")
    private Integer age ;

    @Value("${user.zhangsan.address}")
    private String address;


    @Override
    public String toString() {
        System.out.println("-----------------User------------------");
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
