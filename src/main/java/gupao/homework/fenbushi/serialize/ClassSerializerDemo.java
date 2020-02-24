package gupao.homework.fenbushi.serialize;

public class ClassSerializerDemo {
    public static void main(String[] args) {
        ISerializer serializer = new JavaSerializerWithFile();

        ClassB classB = new ClassB();
        classB.setI(10);
        classB.setS("1");

        byte[] bytes = serializer.serialize(classB);

        ClassB u = serializer.deserialize(bytes,ClassB.class);
        System.out.println(u);
    }
}
