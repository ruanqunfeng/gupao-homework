package gupao.homework.modedesign.proxy.dynamicproxy.jdkproxy;

import gupao.homework.modedesign.proxy.staticproxy.Person;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

public class JDKProxyTest {
    public static void main(String[] args) throws Exception {
        Person obj = (Person) new JDKMeipo().getInstance(new Customer());
        System.out.println(obj.getClass().getName());
        obj.findLove();

        // 通过反编译工具可以查看源代码
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
        FileOutputStream os = new FileOutputStream("C:\\Users\\alan\\Documents\\Work\\gupao-homework\\$Proxy0.class");
        os.write(bytes);
        os.flush();
        os.close();
    }
}
