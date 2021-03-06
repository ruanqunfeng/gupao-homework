package gupao.homework.modedesign.singleton.lazy;

public class LazySingleton {
    private LazySingleton() {}

    // 添加volatile可以阻止jvm的优化
    private volatile static LazySingleton instance;

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                // 为什么还要有判断：因为当两个线程进入第一次判断，有一个阻塞在锁的时候，
                // 随着另一个跑完，阻塞的这个还是会去创建，破坏了单例
                if (instance == null) {
                    instance = new LazySingleton();
                    //1.分配内存给这个对象
                    //2.初始化对象
                    //3.设置lazy指向刚分配的内存地址
                    //4.初次访问对象
                    // 不加volatile会有重排序的问题，可能的执行顺序是1->3->2
                    // 线程二访问时发现instance已经不是null了（但是没有初始化），所以线程二会直接返回instance，然后使用报错。
                }
            }
        }
        return instance;
    }
}
