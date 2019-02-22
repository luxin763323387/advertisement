package com.cn.lx.Index.adunit;

import com.cn.lx.Index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AdUnitIndex implements IndexAware <Long, AdUnitObject> {

    private static Map<Long,AdUnitObject> objectMap;

    static{
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("[AdUnitIndex]-> before add ",objectMap );
        objectMap.put(key,value);
        log.info("[AdUnitIndex]-> after add ",objectMap );
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("[AdUnitIndex] -> before update",objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if(null == oldObject){
            objectMap.put(key,value);
        }else {
            oldObject.update(value);
        }
        log.info("[AdUnitIndex] -> after update",objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("[AdUnitIndex] -> before delete",objectMap);
        objectMap.remove(key);
        log.info("[AdUnitIndex] -> after delete",objectMap);
    }
}
