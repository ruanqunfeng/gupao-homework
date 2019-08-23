package gupao.homework.modedesign.factorymode.abstractfactory;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        IShirtFactory shirtFactory = new ShirtFactory();
        shirtFactory.createMenShirt().create();
        shirtFactory.createWomenShirt().create();
    }
}
