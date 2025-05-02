package base;

import base.data.IDataStorage;
import base.data.MapBasedStorage;
import base.evictors.LRUEvictionAwareDataStorage;
import base.ttlcleaners.EagerTTLCleaner;
import base.ttlcleaners.ITTLCleaner;

import java.util.Map;

public class KeyValueManager {
    private Map<String, IDataStorage> dataStorages;
    private Map<String, ITTLCleaner> ttlCleaners;

    public KeyValueManager() {}

    public void add(String namespace) {
        if (this.dataStorages.containsKey(namespace)) {
            throw new IllegalStateException("Namespace '" + namespace + "' already added.");
        }

        IDataStorage plainDataStorage = new MapBasedStorage(50);
        IDataStorage dataStorage = new LRUEvictionAwareDataStorage(plainDataStorage);

        this.dataStorages.put(namespace, dataStorage);

        ITTLCleaner ttlCleaner = new EagerTTLCleaner(dataStorage);
        this.ttlCleaners.put(namespace, ttlCleaner);
    }

    public void put(String namespace, String key, Object value) {
        if (!this.dataStorages.containsKey(namespace)) {
            throw new IllegalArgumentException("Namespace '" + namespace + "' does not exist.");
        }

        this.dataStorages.get(namespace).put(key, value);
        Logger.debug("'" + namespace + "' key: '" + key + "' value: '" + value + "' added successfully.");
    }

    public void put(String namespace, String key, Object value, int ttlInMinutes) {
        this.put(namespace, key, value);
        this.ttlCleaners.get(namespace).ingestTTL(key, ttlInMinutes);
    }

    public Object get(String namespace, String key) {
        if (!this.dataStorages.containsKey(namespace)) {
            throw new IllegalArgumentException("Namespace '" + namespace + "' does not exist.");
        }

        return this.dataStorages.get(namespace).get(key);
    }

    public void delete(String namespace, String key) {
        if (!this.dataStorages.containsKey(namespace)) {
            throw new IllegalArgumentException("Namespace '" + namespace + "' does not exist.");
        }

        this.dataStorages.get(namespace).delete(key);
        Logger.debug("'" + namespace + "' key: '" + key + "' deleted successfully.");
    }

    public void flushAll(String namespace) {
        if (!this.dataStorages.containsKey(namespace)) {
            throw new IllegalArgumentException("Namespace '" + namespace + "' does not exist.");
        }

        this.dataStorages.get(namespace).flushAll();
        Logger.debug("'" + namespace + "' deleted all successfully.");
    }

    public void listKeys(String namespace) {
        if (!this.dataStorages.containsKey(namespace)) {
            throw new IllegalArgumentException("Namespace '" + namespace + "' does not exist.");
        }

        this.dataStorages.get(namespace).getAllKeys();
    }
}
