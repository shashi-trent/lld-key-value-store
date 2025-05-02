package base.ttlcleaners;

import base.Logger;
import base.data.IDataStorage;

import java.util.Set;
import java.util.TreeSet;

public class EagerTTLCleaner implements ITTLCleaner {
    private final IDataStorage dataStorage;
    private final Set<TTLEntry> entries;

    public EagerTTLCleaner(IDataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.entries = new TreeSet<>();
    }

    @Override
    public void perform() {
        long curEpoch = System.currentTimeMillis();

        for (TTLEntry entry : entries) {
            if (entry.expiryEpoch <= curEpoch) {
                this.dataStorage.delete(entry.key);
                this.entries.remove(entry);
                Logger.debug("'" + entry.key + "' got expired and was deleted successfully.");
            } else break;
        }
    }

    @Override
    public void ingestTTL(String key, int ttlInMinutes) {
        this.entries.add(new TTLEntry(System.currentTimeMillis() + ttlInMinutes * 60000L, key));
    }

    private record TTLEntry(long expiryEpoch, String key) implements Comparable<TTLEntry> {
        @Override
        public int compareTo(TTLEntry o) {
            long val = (this.expiryEpoch - o.expiryEpoch);
            return val == 0 ? 0 : (val < 0 ? -1 : 1);
        }
    }
}
