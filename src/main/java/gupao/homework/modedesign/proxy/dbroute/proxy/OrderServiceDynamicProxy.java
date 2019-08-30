package gupao.homework.modedesign.proxy.dbroute.proxy;

import gupao.homework.modedesign.proxy.dbroute.IOrderService;
import gupao.homework.modedesign.proxy.dbroute.db.DynamicDataSourceEntry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServiceDynamicProxy implements InvocationHandler {
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private IOrderService orderService;

    public Object getInstance(IOrderService orderService) {
        this.orderService = orderService;

        Class clazz = orderService.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(args[0]);
        Object obj = method.invoke(orderService,args);
        after();
        return obj;
    }

    private void before(Object arg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Proxy before method");
        Long time = (Long) arg.getClass().getMethod("getCreateTime").invoke(arg);
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("动态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据。");
        DynamicDataSourceEntry.set(dbRouter);
    }

    private void after() {
        System.out.println("Proxy after method");
    }
}
