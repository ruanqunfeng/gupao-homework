package gupao.homework.modedesign.delegate.simple;

public class EmployeeA implements IEmployee {
    @Override
    public void word(String command) {
        System.out.println("我是员工A，我现在开始干" + command + "工作");
    }
}
