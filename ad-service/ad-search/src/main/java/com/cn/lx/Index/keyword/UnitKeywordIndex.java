package com.cn.lx.Index.keyword;

import com.cn.lx.Index.IndexAware;
import com.cn.lx.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
//倒排索引
//k是String，代表关键词，value是set类型
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    //keyword 到unit的map
    private static Map<String,Set<Long>> keywordUnitMap;
    //推广单元的id到关键词的映射
    private static Map<Long,Set<String>> unitKeywordMap;

    static{
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        //通过关键词获取推广单元支持的关键词
        if(StringUtils.isEmpty(key)){
            return Collections.emptySet();  //没有命中
        }

        Set<Long> result = keywordUnitMap.get(key);
        if(result == null){
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndex -> before add",unitKeywordMap );
        //根据k新增一个
        //第一个传递的key(需要添加的)， 第二个keywordUnitMap， 第三个是 当keywordUnitMap不存在的时候会new出一个新的
        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key,keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIdSet.addAll(value);

        for(Long unitId : value){
            Set<String> keywordSet = CommonUtils.getOrCreate(
                    unitId,unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.add(key);
        }
        log.info("UnitKeywordIndex -> after add",unitKeywordMap );
    }

    @Override
    public void update(String key, Set<Long> value) {
        //更新成本比较高
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitKeywordIndex -> before delete",unitKeywordMap );
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);
        for(Long untiId: value){
            Set<String> keywordSet = CommonUtils.getOrCreate(
                    untiId,unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.remove(key);
        }
        log.info("UnitKeywordIndex -> after delete",unitKeywordMap );
    }

    //匹配方法，匹配某些单元是否包含限制词
    public boolean match(Long unitId, List<String> keywords){
        //判断unitKeywordMap是否包含unitId且unitKeywordMap的unitId是否为空
        if(unitKeywordMap.containsKey(unitId)
                && CollectionUtils.isEmpty(unitKeywordMap.get(unitId))){
            Set<String> unitKeywords = unitKeywordMap.get(unitId);

            //当且仅当 unitKeywords是keywords子集的时候 返回true
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }
        return  false;
    }
}
