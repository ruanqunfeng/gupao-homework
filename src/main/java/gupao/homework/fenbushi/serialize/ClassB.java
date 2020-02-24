package gupao.homework.fenbushi.serialize;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClassB extends ClassA implements Serializable {
    public int i;

    public static int j = 10;
}
