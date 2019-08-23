package gupao.homework.modedesign.factorymode.factorymethod;

import gupao.homework.modedesign.factorymode.simplefactory.IClothes;
import gupao.homework.modedesign.factorymode.simplefactory.Shirt;

public class ShirtFactory implements IClothesFactory {
    @Override
    public IClothes create() {
        return new Shirt();
    }
}
