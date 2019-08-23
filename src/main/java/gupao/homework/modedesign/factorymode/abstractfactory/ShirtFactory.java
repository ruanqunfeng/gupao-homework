package gupao.homework.modedesign.factorymode.abstractfactory;

public class ShirtFactory implements IShirtFactory {
    @Override
    public IMenShirt createMenShirt() {
        return new MenShirt();
    }

    @Override
    public IWomenShirt createWomenShirt() {
        return new WomenShirt();
    }
}
