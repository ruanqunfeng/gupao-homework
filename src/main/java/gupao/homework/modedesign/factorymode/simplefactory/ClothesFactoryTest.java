package gupao.homework.modedesign.factorymode.simplefactory;

public class ClothesFactoryTest {
    public static IClothes create(Class<? extends IClothes> c) {
        try {
            if (c != null) {
                return  c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        IClothes clothes = create(Shirt.class);
        clothes.create();
    }
}
