package ru.otus.cache;


public interface MyListener<K, V> {
    void notify(K key, V value, String action);
}
