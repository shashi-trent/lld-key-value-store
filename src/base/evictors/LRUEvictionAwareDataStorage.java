package base.evictors;

import base.Logger;
import base.data.IDataStorage;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LRUEvictionAwareDataStorage extends AEvictionAwareDataStorage {
    private Set<String> keys;

    public LRUEvictionAwareDataStorage(IDataStorage dataStorage) {
        super(dataStorage);
        this.keys = new LinkedHashSet<>();
    }

    @Override
    public void put(String key, Object value) {
        if(this.keys.contains(key)) {
            this.delete(key);
        }
        else if(this.dataStorage.isAtFullCapacity()) {
            String toBeEvictedKey = this.keys.iterator().next();
            this.delete(toBeEvictedKey);
            Logger.debug("'" + toBeEvictedKey + "' key got evicted successfully.");
        }
        this.dataStorage.put(key, value);
        this.keys.add(key);
    }

    @Override
    public Object get(String key) {
        Object value = null;
        if(this.keys.contains(key)) {
            value = this.dataStorage.get(key);
            this.keys.remove(key);
            this.keys.add(key);
        }
        return value;
    }

    @Override
    public void delete(String key) {
        this.keys.remove(key);
        this.dataStorage.delete(key);
    }

    @Override
    public void flushAll() {
        this.keys.clear();
        this.dataStorage.flushAll();
    }

    @Override
    public List<String> getAllKeys() {
        return this.keys.stream().toList();
    }
}


