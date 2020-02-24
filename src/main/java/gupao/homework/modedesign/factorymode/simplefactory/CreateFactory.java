package gupao.homework.modedesign.factorymode.simplefactory;

/**
 * @author Zed
 * @version 1.0
 * @date 2020/2/23 20:44
 */
public class CreateFactory {
    public IClothes create(Class<? extends IClothes> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz != null) {
            return clazz.newInstance();
        }

        return null;
    }
}
