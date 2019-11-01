package tedu.bao.day21;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AutoPojo<T> {
    private Class<T> type;

    public AutoPojo() {
        Class<T> componentType = (Class<T>) getClass().getComponentType();
        System.out.println(componentType);
    }

    public Class<T> getType() {
        return type;
    }
}
