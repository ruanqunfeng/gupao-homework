package gupao.homework.modedesign.proxy.dbroute;

import gupao.homework.modedesign.proxy.dbroute.proxy.OrderServiceDynamicProxy;
import gupao.homework.modedesign.proxy.dbroute.proxy.OrderServiceStaticProxy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DbRouteProxyTest {
    public static void main(String[] args) {
        try {
            Order order = new Order();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2018/05/11");
            order.setCreateTime(date.getTime());

            //IOrderService orderService = new OrderServiceStaticProxy(new OrderService());
            IOrderService orderService = (IOrderService)new OrderServiceDynamicProxy().getInstance(new OrderService());
            orderService.createOrder(order);
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
