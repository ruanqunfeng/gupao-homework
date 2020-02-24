package gupao.homework.fenbushi.serialize;

public class SerialDemo {
    public static void main(String[] args) {
        ISerializer serializer = new JavaSerializerWithFile();

        User user = new User();
        user.setAge(18);
        user.setName("Zed");
        user.setAmount(1000000000.0);

        byte[] bytes = serializer.serialize(user);

        User u = serializer.deserialize(bytes,User.class);
        System.out.println(u);
    }
}
