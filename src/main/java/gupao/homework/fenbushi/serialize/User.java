package gupao.homework.fenbushi.serialize;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 312520229740737051L;
    private transient String name;
    private int age;
    private transient double amount;

    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(name);
        s.writeDouble(amount);
    }

    private void readObject(ObjectInputStream s) throws IOException,ClassNotFoundException {

        s.defaultReadObject();
        name = (String)s.readObject();
        amount = s.readDouble();
    }
}
