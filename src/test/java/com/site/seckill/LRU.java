package com.site.seckill;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<K,V> extends LinkedHashMap<K,V> {
    public static final int size = 3;
    public LRU(){
        super(size,0.75f,true);
    }
    public boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return size()>size;
    }

}
