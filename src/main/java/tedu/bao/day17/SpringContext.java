package tedu.bao.day17;

import tedu.bao.day17.annotation.*;
import tedu.bao.day17.controller.UserController;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SpringContext {
    private Map<String, Object> map = new HashMap<String, Object>();

    private void autoScan() throws Exception {
        String path = SpringContext.class.getResource("/").getPath();
        path = URLDecoder.decode(path, "utf-8");

        File dir = new File(path);
        StringBuilder sb = new StringBuilder();
        scan(dir, sb);
        autowride();

    }

    ///uiutytiuyi
    private void autowride() throws Exception{
        Collection<Object> collection = map.values();
        for (Object obj : collection){
            Class c = obj.getClass();
            for (Field f : c.getDeclaredFields()){
                f.setAccessible(true);
                if (f.isAnnotationPresent(Autowride.class)){
                    initObject(obj,f);
                }else if (f.isAnnotationPresent(Value.class)){
                    initValue(obj,f);
                }
                f.setAccessible(false);
            }
        }
    }

    private void initObject(Object obj, Field f) throws Exception{
        f.set(obj,get(f.getType()));
    }

    private void initValue(Object obj, Field f) throws Exception {
        Value value = f.getAnnotation(Value.class);
        String name = value.name();
        if ("".equals(name.trim())){
            name = value.value();
        }
        f.set(obj,Configure.get(name));
    }

    private void scan(File dir, StringBuilder sb) throws Exception{

        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                if (sb.length() != 0)
                    name = sb + "." + name;
                if (name.endsWith(".class")){
                    name = name.substring(0,name.length()-1-5);
                    //System.out.println(name);
                    handle(name);
                }
            } else {
                if (sb.length() != 0)
                    sb.append(".");
                sb.append(file.getName());
                scan(file, sb);
                int index = sb.lastIndexOf(".");
                if (index == -1)
                    index = 0;
                sb.delete(index, sb.length());
            }
        }

    }

    private void handle(String name) throws Exception{
        Class<? extends Object> c = Class.forName(name);
        if (c.isAnnotationPresent(Entity.class) ||
                c.isAnnotationPresent(Component.class) ||
                c.isAnnotationPresent(Service.class) ||
                c.isAnnotationPresent(Controller.class)){
            map.put(name,c.newInstance());
        }
    }

    private <T> T get(Class<? extends Object> c){
        //System.out.println(c.getName());
        return (T) map.get(c.getName());
    }

    public static void main(String[] args) {
        SpringContext sc = new SpringContext();
        try {
            sc.autoScan();
            UserController userController = sc.get(UserController.class);
            userController.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(sc.map);
    }
}
