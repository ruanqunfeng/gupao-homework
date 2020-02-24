package gupao.homework.fenbushi.serialize;

/**
 * @author Alan
 */
public interface ISerializer {
    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
