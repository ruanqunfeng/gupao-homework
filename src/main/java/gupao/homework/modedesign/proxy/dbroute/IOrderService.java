package gupao.homework.modedesign.proxy.dbroute;

public interface IOrderService {
    int createOrder(Order order);

    void updateOrder(Order order);
}
