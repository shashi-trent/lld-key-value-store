# lld-key-value-store

## Data Layer

**IDataStorage**

A dumb data storage, provides get, set and basic functionalities only.
It is not aware of any eviction or TTL related logics.

    1. MapBasedStorage : one implementation of the IDataStorage.


## Eviction Layer

**AEvictionAwareDataStorage**

A data storage inherently but the logic of eviction is wrapped around.
It is an abstract class implementing IDataStorage (As it is a data-storage inherently).
and it also contains a IDataStorage inside (kind of like decorator pattern with is & has relation but in its constructor disallowing multiple wrapping of EvictionLogic).

    1. LRUEvictionAwareDataStorage : one concrete implementation of the AEvictionAwareDataStorage.


## TTL Cleaning Layer

**ITTLCleaner**

A ttl cleaning logic container providing apis ingestTTL & perform.

    1. EagerTTLCleaner : An Eager TTL Cleaning logic.

## Coordinator

**KeyValueManager**

This manager is at the front to manage the user queries, it takes queries and calls the dataStorage (or EvictAwareDataStorage) 's logic and TTLCleaner logic whenever necessary.