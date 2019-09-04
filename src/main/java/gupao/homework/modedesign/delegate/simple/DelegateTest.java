package gupao.homework.modedesign.delegate.simple;

public class DelegateTest {
    public static void main(String[] args) {
        new Boss().command("加密",new Leader());
        new Boss().command("架构",new Leader());
    }
}
