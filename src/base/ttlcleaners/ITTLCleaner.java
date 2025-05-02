package base.ttlcleaners;

public interface ITTLCleaner {
    void perform();
    void ingestTTL(String key, int ttlInMinutes);
}
