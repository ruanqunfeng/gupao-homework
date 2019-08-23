package gupao.homework.modedesign.factorymode.abstractfactory;

public interface IShirtFactory {
    IMenShirt createMenShirt();
    IWomenShirt createWomenShirt();
}
