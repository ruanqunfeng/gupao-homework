package gupao.homework.modedesign.factorymode.payment;

/**
 * @author Zed
 * @version 1.0
 * @date 2020/2/24 12:10
 */
public class PaymentFactory {
    public IPayment createPayment(Class<? extends IPayment> clazz) {
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new AliPay();
    }
}
