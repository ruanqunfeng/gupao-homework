package gupao.homework.modedesign.singleton.lazy;

public class LazySingleton {
    private LazySingleton() {}

    // 添加volatile可以阻止jvm的优化
    private volatile static LazySingleton instance;

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                    //1.分配内存给这个对象
                    //2.初始化对象
                    //3.设置lazy指向刚分配的内存地址
                    //4.初次访问对象
                }
            }
        }
        return instance;
    }
}
