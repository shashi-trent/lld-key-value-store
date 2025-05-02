package base.data;

import java.util.List;

public interface IDataStorage {
    void put(String key, Object value);
    Object get(String key);
    void delete(String key);
    void flushAll();
    boolean isAtFullCapacity();
    List<String> getAllKeys();
}
