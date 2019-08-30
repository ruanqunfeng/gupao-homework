package gupao.homework.modedesign.proxy.staticproxy;

public class StaticProsyTest {
    public static void main(String[] args) {
        Father father = new Father(new Son());
        father.findLove();
    }
}
