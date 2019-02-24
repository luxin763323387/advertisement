package com.cn.lx.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {
    //当map中k不存在的时候可以new一个新的value
    public static <K,V>V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){

      return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String...args){
        StringBuilder result =new StringBuilder();
        for(String arg : args){
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }
}
