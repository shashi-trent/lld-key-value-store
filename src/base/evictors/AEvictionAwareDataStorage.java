package base.evictors;

import base.data.IDataStorage;

import java.util.List;

public abstract class AEvictionAwareDataStorage implements IDataStorage {
    protected IDataStorage dataStorage;

    public AEvictionAwareDataStorage(IDataStorage dataStorage) {
        if(dataStorage instanceof AEvictionAwareDataStorage) throw new IllegalArgumentException("Data Storage is already Eviction aware.");
        this.dataStorage = dataStorage;
    }

    @Override
    public boolean isAtFullCapacity() {
        return this.dataStorage.isAtFullCapacity();
    }

    @Override
    public List<String> getAllKeys() {
        return this.dataStorage.getAllKeys();
    }
}
