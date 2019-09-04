package gupao.homework.modedesign.delegate.simple;

import java.util.HashMap;
import java.util.Map;

public class Leader {
    private Map<String,IEmployee> stringIEmployeeMap = new HashMap<>();
    public Leader() {
        stringIEmployeeMap.put("加密",new EmployeeA());
        stringIEmployeeMap.put("架构",new EmployeeB());
    }
    public void work(String command) {
        stringIEmployeeMap.get(command).word(command);
    }
}

