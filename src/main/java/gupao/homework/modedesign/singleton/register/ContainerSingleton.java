package gupao.homework.modedesign.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerSingleton {
    private ContainerSingleton() {}
    private static Map<String,Object> ioc = new ConcurrentHashMap<String, Object>();
    public static Object getInstance(String className) throws Exception {
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object obj = Class.forName(className).newInstance();
                ioc.put(className,obj);
                return obj;
            } else {
                return ioc.get(className);
            }
        }
    }
}
