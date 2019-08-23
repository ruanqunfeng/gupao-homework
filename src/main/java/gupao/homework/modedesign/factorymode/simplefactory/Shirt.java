package gupao.homework.modedesign.factorymode.simplefactory;

public class Shirt implements IClothes {
    @Override
    public void create() {
        System.out.println("Create Shirt");
    }
}
