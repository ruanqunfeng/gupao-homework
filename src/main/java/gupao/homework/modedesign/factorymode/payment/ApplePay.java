package gupao.homework.modedesign.factorymode.payment;

/**
 * Created by Tom.
 */
public class ApplePay implements IPayment {
    @Override
    public void pay() {
        System.out.println("ApplePay");
    }
}
