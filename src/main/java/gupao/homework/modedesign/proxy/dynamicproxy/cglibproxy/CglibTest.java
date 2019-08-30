package gupao.homework.modedesign.proxy.dynamicproxy.cglibproxy;

import org.springframework.cglib.core.DebuggingClassWriter;

public class CglibTest {
    public static void main(String[] args) {
        try {
            // 利用cglib的代理类可以将内存中的class文件写入本地磁盘
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\alan\\Documents\\Work\\gupao-homework\\cglib_proxy_class");

            Customer obj = (Customer) new CglibMeipo().getInstance(Customer.class);
            obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
