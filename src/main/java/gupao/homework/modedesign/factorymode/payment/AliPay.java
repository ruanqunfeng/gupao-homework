package gupao.homework.modedesign.factorymode.payment;

/**
 * Created by Tom.
 */
public class AliPay implements IPayment {
    @Override
    public void pay() {
        System.out.println("AliPay");
    }
}
