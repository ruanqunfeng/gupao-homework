package gupao.homework.modedesign.proxy.dynamicproxy.jdkproxy;

import gupao.homework.modedesign.proxy.staticproxy.Person;

public class Customer implements Person {
    @Override
    public void findLove() {
        System.out.println("高富帅");
    }

    @Override
    public void findJob() {
        System.out.println("年薪50W");
    }
}
