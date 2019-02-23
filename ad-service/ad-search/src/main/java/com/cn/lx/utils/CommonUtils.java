package com.cn.lx.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {
    //当map中k不存在的时候可以new一个新的value
    public static <K,V>V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){

      return map.computeIfAbsent(key, k -> factory.get());
    }
}
