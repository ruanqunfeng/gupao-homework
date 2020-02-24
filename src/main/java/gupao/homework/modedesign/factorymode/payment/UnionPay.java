package gupao.homework.modedesign.factorymode.payment;

import gupao.homework.modedesign.strategy.pay.payport.Payment;

/**
 * Created by Tom.
 */
public class UnionPay implements IPayment {
    @Override
    public void pay() {
        System.out.println("UnionPay");
    }
}
