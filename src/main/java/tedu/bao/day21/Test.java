package tedu.bao.day21;

import tedu.bao.day17.pojo.User;

public class Test {
    public static void main(String[] args) {
        System.out.println(new AutoPojo<User>().getType());
    }
}
