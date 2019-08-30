package gupao.homework.modedesign.proxy.dbroute;

public class OrderService implements IOrderService {
    private OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    @Override
    public int createOrder(Order order) {
        System.out.println("OrderService调用orderDao创建订单");
        return orderDao.insert(order);
    }

    @Override
    public void updateOrder(Order order) {
        System.out.println("OrderService调用orderDao更新订单");
        orderDao.update(order);
    }
}
