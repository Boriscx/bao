package tedu.bao.day17;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ognl.Ognl;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Configure {
    private static Map cfg = new HashMap<String, Object>();

    static{
        try {
            load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void load() throws Exception{
        String path = Configure.class.getResource("/userInfo.yml").getPath();
        path = URLDecoder.decode(path,"utf-8");

        File file = new File(path);

        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        cfg  = om.readValue(file,Map.class);
    }

    /**
     *
     * @param string 形如：%{xxx.xxx.xxx}
     * @return  对应的值
     */
    public static Object get(String string){

        string = string.trim();
        string = string.substring(2,string.length()-1);
        //System.out.println(string);
        try {
            return Ognl.getValue(string, cfg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
