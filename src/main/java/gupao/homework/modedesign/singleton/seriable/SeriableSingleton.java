package gupao.homework.modedesign.singleton.seriable;

import java.io.Serializable;

//反序列化时导致单例破坏
public class SeriableSingleton implements Serializable {
    private SeriableSingleton() {}

    private static final SeriableSingleton instance = new SeriableSingleton();

    public static SeriableSingleton getInstance() {
        return instance;
    }


    private Object readResolve() {
        return instance;
    }
}
