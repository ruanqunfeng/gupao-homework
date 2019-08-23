package gupao.homework.modedesign.factorymode.factorymethod;

import gupao.homework.modedesign.factorymode.simplefactory.IClothes;

public class ClothesFactoryTest {
    public static void main(String[] args) {
        /*IClothesFactory clothesFactory = new ShirtFactory();
        IClothes iClothes = clothesFactory.create();
        iClothes.create();*/

        IClothesFactory clothesFactory = new SkirtFactory();
        IClothes iClothes = clothesFactory.create();
        iClothes.create();
    }
}
