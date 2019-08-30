package gupao.homework.modedesign.proxy.dbroute;

public class OrderDao {
    public int insert(Order order) {
        System.out.println("OrderDao 创建 Order成功");
        return 1;
    }

    public void update(Order order) {
        System.out.println("OrderDao 更新 Order成功");
    }
}
