package com.cn.lx.handler;

import com.cn.lx.DataTable;
import com.cn.lx.Index.IndexAware;
import com.cn.lx.Index.adplan.AdPlanIndex;
import com.cn.lx.Index.adplan.AdPlanObject;
import com.cn.lx.Index.adunit.AdUnitIndex;
import com.cn.lx.Index.adunit.AdUnitObject;
import com.cn.lx.Index.creative.CreativeIndex;
import com.cn.lx.Index.creative.CreativeObject;
import com.cn.lx.Index.creativeunit.CreativeUnitIndex;
import com.cn.lx.Index.creativeunit.CreativeUnitObject;
import com.cn.lx.Index.district.UnitDistrictIndex;
import com.cn.lx.Index.interest.UnitItIndex;
import com.cn.lx.Index.keyword.UnitKeywordIndex;
import com.cn.lx.dump.table.*;
import com.cn.lx.mysql.constant.OpType;
import com.cn.lx.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 1 索引之间存在着层级的划分，也就是以来关系的划分
 * 2 加载全量索引 其实就是增量索引  ”添加“ 的一种特殊关系
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable adPlanTable, OpType type) {
        AdPlanObject adPlanObject = new AdPlanObject(
                adPlanTable.getId(),
                adPlanTable.getUserId(),
                adPlanTable.getPlanStatus(),
                adPlanTable.getStartDate(),
                adPlanTable.getEndDate()
        );
        handleBingLogEvent(
                DataTable.of(AdPlanIndex.class),
                adPlanObject.getPlanId(),
                adPlanObject,
                type
        );
    }

    public static void handleLevel2(AdCreativeTable adCreativeTable, OpType type){
        CreativeObject creativeObject = new CreativeObject(
                adCreativeTable.getAdId(),
                adCreativeTable.getName(),
                adCreativeTable.getType(),
                adCreativeTable.getMaterialType(),
                adCreativeTable.getHeight(),
                adCreativeTable.getWidth(),
                adCreativeTable.getAuditStatus(),
                adCreativeTable.getAdUrl()
        );
        handleBingLogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

   public static void handleLevel3(AdUnitTable adUnitTable,OpType type){
             //在AdUnitIndex中有PlanId
            AdPlanObject adPlanObject = DataTable.of(
                    AdPlanIndex.class
            ).get(adUnitTable.getPlanId());
            if(null == adPlanObject){
                log.error("handleLevel3 found AdPlanObject error: {}");
                return;
            }
            AdUnitObject unitObject = new AdUnitObject(
                    adUnitTable.getUnitId(),
                    adUnitTable.getUnitStatus(),
                    adUnitTable.getPositionType(),
                    adUnitTable.getPlanId(),
                    adPlanObject
            );
            handleBingLogEvent(
                    DataTable.of(AdUnitIndex.class),
                    unitObject.getUnitId(),
                    unitObject,
                    type
            );
   }

   public static void handleLevel3(AdCreativeUnitTable adCreativeUnitTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("CreativeUnitIndex not support update: {}");
            return;
        }

       //在AdUnitIndex中有PlanId
        AdUnitObject adUnitObject = DataTable.of(
                AdUnitIndex.class
        ).get(adCreativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(
               CreativeIndex.class
        ).get(adCreativeUnitTable.getAdId());
        if(null == adUnitObject || null == creativeObject){
            log.error("AdCreativeUnitTable index error: {}");
        }

       CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
               adCreativeUnitTable.getAdId(),
               adCreativeUnitTable.getUnitId()
       );
        handleBingLogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
   }

   public static void handleLevel4(AdUnitDiscrtTable adUnitDiscrtTable, OpType type){

        //首先地域纬度不支持更新
        if(type == OpType.UPDATE){
            log.error("district index can not support update");
            return;
        }

        //获取当前系统的索引对象
       AdUnitObject adUnitObject = DataTable.of(
               AdUnitIndex.class
       ).get(adUnitDiscrtTable.getUnitId());
        //如果索引不存在，不需要创建
        if(adUnitObject == null){
            log.error("AdUnitDistrictTable index error: {}",adUnitDiscrtTable.getUnitId());
            return;
        }

        //构造Key  省和市用了连字符
        String key = CommonUtils.stringConcat(
                adUnitDiscrtTable.getProvince(),
                adUnitDiscrtTable.getCity()
        );
       Set<Long> value = new HashSet<>(
               Collections.singleton(adUnitDiscrtTable.getUnitId())
       );
       handleBingLogEvent(
               DataTable.of(UnitDistrictIndex.class),
               key, value,
               type);

   }

   public static void handleLevel4(AdUnitItTable adUnitItTable,OpType type){

        //兴趣纬度不支持更新
        if(type  == OpType.UPDATE){
            log.error("it index can not support update");
            return;
        }

        //获取当前所有对象
       AdUnitObject adUnitObject = DataTable.of(
               AdUnitIndex.class
       ).get(adUnitItTable.getUnitId());
        //如果所有不存在，不需要创建
       if(adUnitObject == null){
           log.error("adUnitTable index error: {}",adUnitItTable.getUnitId());
           return;
       }

       Set<Long> value = new HashSet<>(
               Collections.singleton(adUnitItTable.getUnitId())
       );
       handleBingLogEvent(
               DataTable.of(UnitItIndex.class),
               adUnitItTable.getItTag(),
               value,
               type
       );

   }

   public static void handleLevel4(AdUnitKeywordTable adUnitKeywordTable, OpType type){

        if(type == OpType.UPDATE){
            log.error("keyword index can not support update");
            return;
        }

        //获取当前对象
       AdUnitObject adUnitObject = DataTable.of(
               AdUnitIndex.class
       ).get(adUnitKeywordTable.getUnitId());
        //如果不存在，不需要创造
       if(adUnitObject.getUnitId() == null){
           log.error("AdUnitKeywordTable index error: {}",adUnitKeywordTable.getUnitId());
           return;
       }

       Set<Long> value = new HashSet<>(
               Collections.singleton(adUnitKeywordTable.getUnitId())
       );

       handleBingLogEvent(
               DataTable.of(UnitKeywordIndex.class),
               adUnitKeywordTable.getKeyword(),
               value,
               type
       );

   }

    //通用方法处理全量索引，和增量索引，监听binglog
    private static <K, V> void handleBingLogEvent(
            IndexAware<K, V> index,
            K key,
            V value,
            OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
