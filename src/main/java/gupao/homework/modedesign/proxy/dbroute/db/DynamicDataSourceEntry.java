package gupao.homework.modedesign.proxy.dbroute.db;

public class DynamicDataSourceEntry {

    // 默认数据源
    public final static String DEFAULT_SOURCE = null;

    private final static ThreadLocal<String> Local = new ThreadLocal<>();

    private DynamicDataSourceEntry() {}

    //清空数据源
    public static void clear() {
        Local.remove();
    }

    //获取当前正在使用的数据源名字
    public static String get() {
        return Local.get();
    }

    //还原当前切面的数据源
    public static void restore() {
        Local.set(DEFAULT_SOURCE);
    }

    //设置已知名字的数据源
    public static void set(String source) {
        Local.set(source);
    }

    //根据年份动态设置数据源
    public static void set(int year) {
        Local.set("DB_" + year);
    }
}
