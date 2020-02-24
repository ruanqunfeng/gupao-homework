package gupao.homework.modedesign.factorymode.payment;

/**
 * @author Zed
 * @version 1.0
 * @date 2020/2/24 12:22
 */
public class Test {
    public static void main(String[] args) {
        IPayment payment = new PaymentFactory().createPayment(WechatPay.class);
        payment.pay();
    }
}
