package com.cn.lx.Index.adplan;

import com.cn.lx.Index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    private  static Map<Long, AdPlanObject> objectMap;
    static{
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("[AdPlanIndex] -> before add ",objectMap );
        objectMap.put(key,value);
        log.info("[AdPlanIndex] -> after add ",objectMap );
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("[AdPlanIndex] -> before update",objectMap);
        AdPlanObject adPlanObject = objectMap.get(key);
        if(adPlanObject == null){
            objectMap.put(key, value);
        }else {
            adPlanObject.update(value);
        }
       log.info("[AdPlanIndex] -> after update",objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("[AdPlanIndex] -> before delete",objectMap);
        objectMap.remove(key);
        log.info("[AdPlanIndex] -> after delete",objectMap);
    }
}
