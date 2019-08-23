package gupao.homework.modedesign.factorymode.factorymethod;

import gupao.homework.modedesign.factorymode.simplefactory.IClothes;
import gupao.homework.modedesign.factorymode.simplefactory.Skirt;

public class SkirtFactory implements IClothesFactory {
    @Override
    public IClothes create() {
        return new Skirt();
    }
}
