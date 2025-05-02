package base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBasedStorage implements IDataStorage {
    private Map<String, Object> data;
    private final int capacity;

    public MapBasedStorage(int capacity) {
        this.capacity = capacity;
        this.data = new HashMap<>();
    }

    @Override
    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public Object get(String key) {
        return this.data.getOrDefault(key, null);
    }

    @Override
    public void delete(String key) {
        this.data.remove(key);
    }

    @Override
    public void flushAll() {
        this.data.clear();
    }

    @Override
    public boolean isAtFullCapacity() {
        return this.data.size() == this.capacity;
    }

    @Override
    public List<String> getAllKeys() {
        return this.data.keySet().stream().toList();
    }
}
