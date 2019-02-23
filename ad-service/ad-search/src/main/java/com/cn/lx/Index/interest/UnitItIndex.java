package com.cn.lx.Index.interest;

import com.cn.lx.Index.IndexAware;
import org.apache.commons.collections4.CollectionUtils;
import com.cn.lx.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {
    //k 是itTag 兴趣标签， v是unitId set
    // <itTag, adUnitId set> 反向索引
    private static Map<String, Set<Long>> itUnitMap;
    //<unitId, itTag set> 正向
    private static Map<Long, Set<String>> unitItMap;

    static{
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
    log.info("[UnitItIndex] -> before add",unitItMap);
    //通过key 获取unitIds
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key,itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);
        for(Long unitId : value){
            Set<String> its = CommonUtils.getOrCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            its.add(key);
        }
        log.info("[UnitItIndex] -> after add",unitItMap);
    }


    @Override
    public void update(String key, Set<Long> value) {
        //更新成本比较高
        log.error("interest index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("[UnitItIndex] -> before delete",unitItMap);

        //先把itUnitMap对应k 把unitIds拿出来
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key,itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);
        for(Long unitId : value){
            Set<String> itTagSet = CommonUtils.getOrCreate(
                    unitId,unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.remove(key);
        }
        log.info("[UnitItIndex] -> after delete",unitItMap);
    }

    //匹配方法
    public boolean match(Long unitId, List<String> itTags){
        if(unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))){

            Set<String> unitIt = unitItMap.get(unitId);

            //当且仅当，itTags 是 unitIt子集的时候返回ture
            return CollectionUtils.isSubCollection(itTags,unitIt);
        }
        return false;
    }
}
